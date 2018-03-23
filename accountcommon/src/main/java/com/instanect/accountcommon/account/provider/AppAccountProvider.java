package com.instanect.accountcommon.account.provider;

import android.content.Context;

import com.instanect.mainapp.layers.business.authentication.account.AppAccount;
import com.instanect.mainapp.layers.business.authentication.account.AppAccountInterface;

/**
 * Get new AppAccount Object
 */

public class AppAccountProvider {

    public AppAccount provideAppAccount(Context context, AppAccountInterface listener) {

        return new AppAccount(context, listener);
    }
}
