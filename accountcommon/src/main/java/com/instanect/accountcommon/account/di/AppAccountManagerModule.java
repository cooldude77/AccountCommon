package com.instanect.accountcommon.account.di;

import android.accounts.AccountManager;
import android.content.Context;

import com.instanect.mainapp.layers.business.authentication.account.AppAccountManager;
import com.instanect.mainapp.layers.business.authentication.factory.AppAccountCreateBundleFactory;
import com.instanect.mainapp.layers.business.authentication.factory.AppAccountCreateFactory;
import com.instanect.mainapp.layers.business.declarations.AccountDetailsDeclaration;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AKS on 10/31/2017.
 */
@Module
public class AppAccountManagerModule {

    private Context context;

    public AppAccountManagerModule(Context context) {

        this.context = context;
    }

    @Provides
    public AppAccountManager provideAppAccountManager() {


        AccountManager manager = AccountManager.get(context);
        return new AppAccountManager(manager,
                new AccountDetailsDeclaration(context),
                new AppAccountCreateFactory(),
                new AppAccountCreateBundleFactory());


    }
}
