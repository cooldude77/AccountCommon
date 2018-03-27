package com.instanect.accountcommon.account;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AKS on 12/19/2017.
 */

@Module
public class AndroidProviderModule {

    @Provides
    HashMapProvider provideHashMapProvider() {
        return new HashMapProvider();
    }

    @Provides
    BundleProvider provideBundleProvider() {
        return new BundleProvider();
    }
}
