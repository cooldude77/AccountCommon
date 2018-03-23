package com.instanect.accountcommon.account.provider.di;

import com.instanect.mainapp.layers.business.authentication.account.provider.AppAccountProvider;

import dagger.Module;
import dagger.Provides;

/**
 * AppAccountProviderModule
 */

@Module
public class AppAccountProviderModule {

    @Provides
    public AppAccountProvider provideAppAccountProvider() {
        return new AppAccountProvider();
    }

}
