package com.rbs.cache;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;

/**
 * 
 * @author Rodrigo Dellinghausen (dellinghausen@gmail.com)
 *
 * @since 03/10/2014
 */
public class CacheControl {

	
    private static CacheControl instance;

    private final Map<String, String> cacheLRU; 

    @SuppressWarnings("unchecked")
    private CacheControl() {
        this.cacheLRU = Collections.synchronizedMap( new LRUMap(70) );
    }

    public static CacheControl getInstance() {
        if (instance == null) {
            instance = new CacheControl();
        }

        return instance;
    }

    public String get(String key) {
        return cacheLRU.get(key);
    }

    public void put(String key, String value) {
        cacheLRU.put(key, value);
    }
}
