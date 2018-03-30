package com.instanect.accountcommon.account.di;

import android.accounts.AccountManager;
import android.content.Context;

import com.instanect.accountcommon.account.AccountDetailsDeclarationInterface;
import com.instanect.accountcommon.account.account.operations.concrete.AccountCreate;
import com.instanect.accountcommon.account.account.operations.concrete.AccountQuery;
import com.instanect.accountcommon.account.factory.AppAccountCreateBundleFactory;
import com.instanect.accountcommon.account.factory.AppAccountCreateFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AKS on 3/25/2018.
 */
@Module
public class AccountCommonModule {

    private final Context context;
    private final AccountManager accountManager;
    private final AccountDetailsDeclarationInterface accountDetailsDeclarationInterface;

    public AccountCommonModule(Context context, AccountDetailsDeclarationInterface accountDetailsDeclarationInterface) {

        this.context = context;

        accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        this.accountDetailsDeclarationInterface = accountDetailsDeclarationInterface;
    }

    @Provides
    AccountCreate provideAccountCreate() {
        return new AccountCreate(
                accountManager,
                new AppAccountCreateFactory(),
                new AppAccountCreateBundleFactory(),
                accountDetailsDeclarationInterface
        );
    }

    @Provides
    AccountQuery provideAccountQuery() {
        return new AccountQuery(
                context,
                accountManager,
                accountDetailsDeclarationInterface
        );
    }
}
