package com.instanect.accountcommon.account;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


/**
 * Manages and retrieves account information. Normally used in Activity classes
 */
public class AppAccount extends AsyncTask<Void, Void, Void> {
    private final Context context;
    private final AppAccountInterface appAccountInterface;
    private final AccountDetailsDeclarationInterface accountDetailsDeclarationInterface;
    private Account account;
    private Boolean otherErrorOccurred = false;
    private Boolean authenticationErrorOccurred = false;
    private AccountManager accountManager;

    private String username;

    private String authToken;


    public AppAccount(
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
            accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

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

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (!(otherErrorOccurred || authenticationErrorOccurred)) {
            appAccountInterface.onAccountDetailsRetrieved(this);
        } else {
            if (authenticationErrorOccurred)
                appAccountInterface.onAccountAuthenticationFailure();
            else if (otherErrorOccurred)
                appAccountInterface.onAccountRetrievalFailure();
        }
    }

    public String getUsername() {
        return username;
    }


    public void invalidateToken() {

        accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        checkPermission();
        Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclarationInterface.getAccountType());

        account = accounts[0];

        String authToken = accountManager.peekAuthToken(account, accountDetailsDeclarationInterface.getAuthTokenType());
// validateInput the token, invalidate and generate a new one if required
        accountManager.invalidateAuthToken(accountDetailsDeclarationInterface.getAccountType(), authToken);
    }

    public boolean hasAccount() {

        try {

            accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

            checkPermission();
            Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclarationInterface
                    .getAccountType());

            account = accounts[0];

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Account getAccount() {
        return account;
    }

    public String getAuthToken() {
        return authToken;
    }

    /**
     * @throws NullPointerException when there is no account
     */
    public void deleteAccount() throws NullPointerException {


        accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        checkPermission();

        Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclarationInterface
                .getAccountType());

        if (accounts.length == 0)
            throw new NullPointerException("There are no accounts related to the app");


        account = accounts[0];

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            accountManager.removeAccount(account, null, null);
        else
            accountManager.removeAccountExplicitly(account);

    }

    private void checkPermission() {

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
    }

    /**
     * @return
     * @throws NullPointerException
     */
    public String getEmail() throws NullPointerException {
        if (account == null)
            throw new NullPointerException("Account does not exist");
        else
            return account.name;
    }
}