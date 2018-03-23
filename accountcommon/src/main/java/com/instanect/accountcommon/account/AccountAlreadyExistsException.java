package com.instanect.accountcommon.account;

/**
 * Created by AKS on 10/11/2017.
 */

public class AccountAlreadyExistsException extends Exception {

    public AccountAlreadyExistsException() {
        super("Account already exists Exception");
    }

}
