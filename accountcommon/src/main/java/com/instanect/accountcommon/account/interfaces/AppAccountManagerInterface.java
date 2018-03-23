package com.instanect.accountcommon.account.interfaces;

import com.instanect.mainapp.layers.business.authentication.account.AccountAlreadyExistsException;

/**
 * Created by AKS on 10/7/2017.
 */

public interface AppAccountManagerInterface {
    void addNewAccount(String email, String token, String username)
            throws AccountAlreadyExistsException;

    void deleteDefaultAccount();

    boolean accountExists();

    void createAccount(String email, String token, String username) throws Exception;
}
