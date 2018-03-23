package com.instanect.accountcommon.account;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.instanect.mainapp.BuildConfig;
import com.instanect.mainapp.layers.business.authentication.account.interfaces.AppAccountManagerInterface;
import com.instanect.mainapp.layers.business.authentication.factory.AppAccountCreateBundleFactory;
import com.instanect.mainapp.layers.business.authentication.factory.AppAccountCreateFactory;
import com.instanect.mainapp.layers.business.declarations.AccountDetailsDeclaration;
import com.instanect.mainapp.layers.business.util.LogTagGenerator;

/**
 * App account manager is used to add a new account or update a token
 */
public class AppAccountManager implements AppAccountManagerInterface {

    private final AccountManager accountManager;
    private final AccountDetailsDeclaration accountDetailsDeclaration;
    private final String TAG = LogTagGenerator.getTag(AppAccountManager.class);
    private final AppAccountCreateFactory appAccountCreateFactory;
    private final AppAccountCreateBundleFactory appAccountCreateBundleFactory;
    private Account account;

    public AppAccountManager(AccountManager accountManager,
                             AccountDetailsDeclaration accountDetailsDeclaration,
                             AppAccountCreateFactory appAccountCreateFactory,
                             AppAccountCreateBundleFactory appAccountCreateBundleFactory) {
        this.accountManager = accountManager;
        this.accountDetailsDeclaration = accountDetailsDeclaration;
        this.appAccountCreateFactory = appAccountCreateFactory;
        this.appAccountCreateBundleFactory = appAccountCreateBundleFactory;
        Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclaration.getAccountType());
        if (accounts.length > 0)
            account = accounts[0];

    }

    /**
     * @return if account exists
     */
    public boolean checkAccountExistsByType() {
        Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclaration.getAccountType());
        return accounts.length > 0;
    }

    /**
     * @return email used to login
     */
    public String getEmail() {

        if (account != null)
            return account.name;
        return null;

    }

    /**
     * @return username (identifier in server)
     */
    public String getUsername() {

        if (account != null) {
            return accountManager.getUserData(account, "username");

        }
        return null;
    }

    /**
     * @param email     Email used to login
     * @param authToken Authorization Token
     * @param username  Username on server
     */
    public void addNewAccount(String email, String authToken, String username) {


    }

    @Override
    public void deleteDefaultAccount() {

        checkPermission();

        Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclaration.getAccountType());

        if (accounts.length == 0)
            throw new NullPointerException("There are no accounts related to the app");


        account = accounts[0];

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            accountManager.removeAccount(account, null, null);
        else
            accountManager.removeAccountExplicitly(account);

    }

    @Override
    public boolean accountExists() {
        return checkAccountExistsByType();
    }

    @Override
    public void createAccount(String email, String token, String username)
            throws Exception {
        if (checkAccountExistsByType())
            throw new IllegalArgumentException("Account already exists");

        Account account = appAccountCreateFactory.getAccount(email,
                accountDetailsDeclaration.getAccountType());
        Bundle bundle = appAccountCreateBundleFactory.getBundle("username", username);

        ContentResolver.setIsSyncable(account, BuildConfig.GRADLE_APP_AUTHORITY, 1);
        ContentResolver.setSyncAutomatically(account, BuildConfig.GRADLE_APP_AUTHORITY, true);

        ContentResolver.addPeriodicSync(account, BuildConfig.GRADLE_APP_AUTHORITY, bundle, 60 * 60 * 24);
        // add this account
        accountManager.addAccountExplicitly(account, null, bundle);
        // set authToken received from server
        accountManager.setAuthToken(account, accountDetailsDeclaration.getAuthTokenType(), token);

        Log.d(TAG, "New Account Added");
        Log.d(TAG, "Account Name : " + account.name);
        Log.d(TAG, "Account Token: " + token);
        Log.d(TAG, "Account set to is syncable");
        Log.d(TAG, "Account set to automatically sync");
        Log.d(TAG, "Periodic sync added");

    }

    /**
     * Update Password
     *
     * @param password password
     */
    public void updatePassword(String password) {

        accountManager.setPassword(account, password);
    }

    /**
     * Update Token
     *
     * @param token token
     */
    public void updateToken(String token) {
        accountManager.setAuthToken(account, accountDetailsDeclaration.getAuthTokenType(), token);
    }

    /**
     * @throws IllegalArgumentException if permission is missing
     * @returns token .
     */
    public String getAuthToken() throws IllegalArgumentException {

        checkPermission();

        Account[] accounts = accountManager.getAccountsByType(accountDetailsDeclaration.getAccountType());

        account = accounts[0];

        return accountManager.peekAuthToken(account, accountDetailsDeclaration.getAuthTokenType());
    }

    private void checkPermission() {

        if (ActivityCompat.checkSelfPermission(accountDetailsDeclaration.getContext(),
                Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
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

}
