package com.instanect.accountcommon;

import java.util.HashMap;

/**
 * Created by AKS on 1/10/2017.
 */
public class HashMapProvider implements HashMapInterface {


    @Override
    public HashMap<String, String> getHashMap() {
        return new HashMap<String, String>();
    }

    public <T, K> HashMap<T, K> getHashMap(Class<T> tClass, Class<K> kClass) {
        return new HashMap<T, K>();
    }
}
