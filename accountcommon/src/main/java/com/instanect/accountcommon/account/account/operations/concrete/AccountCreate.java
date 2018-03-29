package com.instanect.accountcommon.account.account.operations.concrete;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;

import com.instanect.accountcommon.account.AccountDetailsDeclarationInterface;
import com.instanect.accountcommon.account.account.operations.interfaces.AccountCreateInterface;
import com.instanect.accountcommon.account.account.operations.interfaces.response.AccountCreateResponseInterface;
import com.instanect.accountcommon.account.factory.AppAccountCreateBundleFactory;
import com.instanect.accountcommon.account.factory.AppAccountCreateFactory;
import com.instanect.accountcommon.account.misc.LogTagGenerator;

/**
 * Class to create account
 */

public class AccountCreate implements AccountCreateInterface {

    private final String TAG = LogTagGenerator.getTag(AccountCreate.class);
    private final AccountManager accountManager;
    private final AppAccountCreateFactory appAccountCreateFactory;
    private final AppAccountCreateBundleFactory appAccountCreateBundleFactory;
    private final AccountDetailsDeclarationInterface accountDetailsDeclarationInterface;
    private AccountCreateResponseInterface accountInteractorInterface;

    public AccountCreate(
            AccountManager accountManager,
            AppAccountCreateFactory appAccountCreateFactory,
            AppAccountCreateBundleFactory appAccountCreateBundleFactory,
            AccountDetailsDeclarationInterface accountDetailsDeclarationInterface
    ) {
        this.accountManager = accountManager;

        this.appAccountCreateFactory = appAccountCreateFactory;
        this.appAccountCreateBundleFactory = appAccountCreateBundleFactory;
        this.accountDetailsDeclarationInterface = accountDetailsDeclarationInterface;
    }

    @Override
    public void createAccount(
            String email,
            String token,
            String username,
            String appAuthority
    ) throws Exception {

        Account account = appAccountCreateFactory.getAccount(email,
                accountDetailsDeclarationInterface.getAccountType());
        Bundle bundle = appAccountCreateBundleFactory.getBundle("username", username);

        ContentResolver.setIsSyncable(account, appAuthority, 1);
        ContentResolver.setSyncAutomatically(account, appAuthority, true);

        ContentResolver.addPeriodicSync(account, appAuthority, bundle, 60 * 60 * 24);
        // add this account
        accountManager.addAccountExplicitly(account, null, bundle);
        // set authToken received from server
        accountManager.setAuthToken(account, accountDetailsDeclarationInterface.getAuthTokenType(), token);

        Log.d(TAG, "New Account Added");
        Log.d(TAG, "Account Name : " + account.name);
        Log.d(TAG, "Account Token: " + token);
        Log.d(TAG, "Account set to is syncable");
        Log.d(TAG, "Account set to automatically sync");
        Log.d(TAG, "Periodic sync added");

    }

    @Override
    public void setAccountCreateResponseInterface(AccountCreateResponseInterface accountInteractorInterface) {

        this.accountInteractorInterface = accountInteractorInterface;
    }
}
