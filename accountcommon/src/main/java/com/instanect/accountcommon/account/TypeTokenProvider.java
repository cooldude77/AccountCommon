package com.instanect.accountcommon.account;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by AKS on 3/21/2018.
 */

public class TypeTokenProvider {
    public static Type getArrayListStringType() {
        return new TypeToken<ArrayList<String>>() {
        }.getType();
    }
}
