package com.instanect.accountcommon.account.di;

import com.instanect.mainapp.layers.business.authentication.account.AppAccountManager;

import dagger.Component;

/**
 * Created by AKS on 10/31/2017.
 */

@Component(modules = {AppAccountManagerModule.class})
public interface AppAccountManagerComponent {
    AppAccountManager getAppAccountManager();
}
