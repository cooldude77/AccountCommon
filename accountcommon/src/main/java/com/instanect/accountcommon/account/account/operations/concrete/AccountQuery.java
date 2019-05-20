package com.instanect.accountcommon.account.account.operations.concrete;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.instanect.accountcommon.account.AccountDetailsDeclarationInterface;
import com.instanect.accountcommon.account.account.operations.interfaces.AccountQueryInterface;
import com.instanect.accountcommon.account.account.operations.interfaces.response.AccountQueryResponseInterface;

/**
 * Created by AKS on 3/23/2018.
 */

public class AccountQuery extends AsyncTask<Void, Void, Void> implements AccountQueryInterface{
    private final AccountDetailsDeclarationInterface accountDetailsDeclarationInterface;
    private Account account;
    private Boolean otherErrorOccurred = false;
    private Boolean authenticationErrorOccurred = false;
    private final Context context;
    private AccountManager accountManager;

    private String username;
    private String authToken;
    private AccountQueryResponseInterface accountInteractorInterface;

    public AccountQuery(
            Context context,
            AccountManager accountManager,
            AccountDetailsDeclarationInterface accountDetailsDeclarationInterface) {
        this.context = context;
        this.accountManager = accountManager;
        this.accountDetailsDeclarationInterface = accountDetailsDeclarationInterface;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                throw new IllegalArgumentException("GET_ACCOUNTS Permission not available");
            }

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

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public void setAccountQueryResponseInterface(AccountQueryResponseInterface accountInteractorInterface) {

        this.accountInteractorInterface = accountInteractorInterface;
    }
}
