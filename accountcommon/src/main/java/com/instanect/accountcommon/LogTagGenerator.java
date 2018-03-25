package com.instanect.accountcommon;

/**
 * Created by AKS on 6/5/2017.
 */

public class LogTagGenerator {
    public static String getTag(Class<?> className) {
        return "Inst-" + className.toString();
    }
}
