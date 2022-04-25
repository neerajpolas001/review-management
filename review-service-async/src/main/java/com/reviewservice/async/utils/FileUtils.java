package com.reviewservice.async.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.reviewservice.async.exceptions.ReviewServiceAsyncException;
import com.reviewservice.utils.StringUtils;

public class FileUtils {
	public static File createDirectoryIfnotExist(String directorypath) throws ReviewServiceAsyncException {
		File directory = new File(directorypath);
//		if (!directory.isDirectory())
//			throw new ReviewServiceAsyncException("Directory path : '" + directorypath + "', is not a directory");
		if (!directory.exists())
			directory.mkdirs();
		return directory;
	}

	public static File createFile(File directory, String fileName) throws ReviewServiceAsyncException {
		if (directory == null)
			throw new ReviewServiceAsyncException("prameter directory can not be null");
		if (StringUtils.isEmptyOrBlank(fileName))
			throw new ReviewServiceAsyncException("fileName can not be null/Empty/blank");
		if (!directory.isDirectory())
			throw new ReviewServiceAsyncException("Directory path : '" + directory.getAbsolutePath() + "', is not a directory");
		if (!directory.exists())
			throw new ReviewServiceAsyncException("Directory path : '" + directory.getAbsolutePath() + "', does not exist");
		File file = new File(directory, fileName);
		if (file.exists())
			file.delete();
		return new File(directory, fileName);
	}

	public static List<File> getAllFilesFromDirectory(String directorypath) throws ReviewServiceAsyncException {
		File directory = new File(directorypath);
		if (!directory.exists())
			throw new ReviewServiceAsyncException("Directory : '" + directorypath + "', does not exist");
		return Arrays.stream(directory.listFiles()).filter(f -> f.isFile()).collect(Collectors.toList());
	}

	public static long getFileSizeInKB(File file) throws ReviewServiceAsyncException {
		if (!file.exists())
			throw new ReviewServiceAsyncException("file : '" + file + "', does not exist");
		if (!file.isFile())
			throw new ReviewServiceAsyncException("file : '" + file + "', is not a file");
		return file.length() / 1024;
	}

}
