package com.reviewservice.utils;

import java.util.Collection;

public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> list) {
	return list == null || list.isEmpty();
    }
}
