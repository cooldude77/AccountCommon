package com.instanect.accountcommon.account;

import android.accounts.Account;

/**
 * Created by AKS on 5/10/2016.
 */
public class AppAccountCreateFactory {
    public Account getAccount(String email, String accountType) {
        return new Account(email, accountType);
    }
}
