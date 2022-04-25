package com.reviewservice.async.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.reviewservice.async.exceptions.ReviewServiceAsyncException;

public class ReportGenerator<T> {
	private BufferedWriter bufferedWriter;
	private File directory;
	private File file;
	private String userId;
	private int fileCount = 0;
	private List<String> headers;
	private Field[] declaredFields;

	public ReportGenerator(Class<T> clazz, String userId, String directoryPath) throws ReviewServiceAsyncException, IOException {
		super();
		this.headers = getHeaders(clazz);
		this.declaredFields = clazz.getDeclaredFields();
		this.directory = FileUtils.createDirectoryIfnotExist(directoryPath + "/" + userId);
		this.file = FileUtils.createFile(directory, userId + ".csv");
		this.bufferedWriter = new BufferedWriter(new FileWriter(file));
		writeline(headers, bufferedWriter);
	}

	public void write(List<T> reviews, long fileSizeInKB) throws ReviewServiceAsyncException, IOException, IllegalArgumentException, IllegalAccessException {
		if (FileUtils.getFileSizeInKB(file) >= fileSizeInKB) {
			bufferedWriter.flush();
			bufferedWriter.close();
			bufferedWriter = new BufferedWriter(new FileWriter(file = FileUtils.createFile(directory, userId + "-" + fileCount + ".csv")));
			writeline(headers, bufferedWriter);
		}
		List<List<String>> listOfRows = this.getListOfFieldValues(reviews);
		for (List<String> row : listOfRows) {
			writeline(row, bufferedWriter);
		}
		bufferedWriter.flush();
	}

	private List<String> getHeaders(Class<T> clazz) {
		List<String> headers = Arrays.stream(clazz.getDeclaredFields()).map(f -> f.getName()).collect(Collectors.toList());
		return headers;
	}

	private void writeline(List<String> line, BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write(String.join(",", line));
		bufferedWriter.newLine();
		bufferedWriter.flush();
	}

	private List<List<String>> getListOfFieldValues(List<T> ts) throws IllegalArgumentException, IllegalAccessException {
		List<List<String>> listOfRows = new ArrayList<>();
		for (T t : ts) {
			List<String> row = new ArrayList<>();
			for (Field field : declaredFields) {
				field.setAccessible(true);
				Object value = field.get(t);
				if (value instanceof Map || value instanceof List)
					value = "\"" + value.toString() + "\"";
				row.add((value == null) ? "" : value.toString());
			}
			listOfRows.add(row);
		}
		return listOfRows;
	}

	public void close() throws IOException {

		bufferedWriter.close();
	}

}
