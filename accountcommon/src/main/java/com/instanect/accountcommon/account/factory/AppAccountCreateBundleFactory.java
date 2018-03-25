package com.instanect.accountcommon.account.factory;

import android.os.Bundle;

/**
 * Created by AKS on 5/10/2016.
 */
public class AppAccountCreateBundleFactory {


    public Bundle getBundle(String usernameKey, String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username not found");
        }

        Bundle bundle = new Bundle();

        // put username from server
        bundle.putString(usernameKey, username);

        return bundle;
    }

}
