package com.reviewserivce.persitance.uuid;

import java.util.UUID;

public class UID {

//    @Value("${UUID.key}")
//    private String key1;

    //private static String key = "reviewservicekey";

//    @Value("${UUID.key}")
//    public void setKey(String key1) {
//	UID.key = key1;
//    }

    public static String getUUID() {
	return UUID.randomUUID().toString();
    }

}
