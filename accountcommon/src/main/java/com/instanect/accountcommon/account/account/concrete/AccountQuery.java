package com.instanect.accountcommon.account.account.concrete;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.instanect.accountcommon.account.AccountDetailsDeclarationInterface;
import com.instanect.accountcommon.account.AppAccountInterface;
import com.instanect.accountcommon.account.account.interfaces.AccountQueryInterface;

/**
 * Created by AKS on 3/23/2018.
 */

public class AccountQuery extends AsyncTask<Void, Void, Void> implements AccountQueryInterface {
    private final Context context;
    private final AppAccountInterface appAccountInterface;
    private final AccountDetailsDeclarationInterface accountDetailsDeclarationInterface;
    private Account account;
    private Boolean otherErrorOccurred = false;
    private Boolean authenticationErrorOccurred = false;
    private AccountManager accountManager;

    private String username;

    private String authToken;


    public AccountQuery(
            Context context,
            AppAccountInterface appAccountInterface,
            AccountDetailsDeclarationInterface accountDetailsDeclarationInterface) {
        this.context = context;
        this.appAccountInterface = appAccountInterface;
        this.accountDetailsDeclarationInterface = accountDetailsDeclarationInterface;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            AccountManager accountManager = (AccountManager) context
                    .getSystemService(Context.ACCOUNT_SERVICE);

//            checkPermission();
            Account[] accounts = accountManager
                    .getAccountsByType(accountDetailsDeclarationInterface.getAccountType());

            account = accounts[0];

            authToken = accountManager.blockingGetAuthToken(account,
                    accountDetailsDeclarationInterface.getAuthTokenType(), true);


            username = accountManager.getUserData(account,
                    accountDetailsDeclarationInterface.getUsernameString());


        } catch (AuthenticatorException e) {
            authenticationErrorOccurred = true;
            Log.d("Instanect-Catch", Log.getStackTraceString(e));
        } catch (OperationCanceledException e) {
            otherErrorOccurred = true;
            Log.d("Instanect-Catch", Log.getStackTraceString(e));
        } catch (Exception e) {
            otherErrorOccurred = true;
        }
        return null;

    }

}
