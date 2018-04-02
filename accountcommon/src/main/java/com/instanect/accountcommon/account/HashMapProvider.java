package com.instanect.accountcommon.account;

import java.util.HashMap;

/**
 * Created by AKS on 1/10/2017.
 */
public class HashMapProvider implements HashMapInterface {


    @Override
    public HashMap<String, String> getHashMap() {
        return HashMapProvider.getHashMap(String.class, String.class);
    }

    public static <T, K> HashMap<T, K> getHashMap(Class<T> tClass, Class<K> kClass) {
        return new HashMap<T, K>();
    }
}
