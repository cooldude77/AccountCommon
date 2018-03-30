package com.instanect.accountcommon.account.account.operations.interfaces;

import com.instanect.accountcommon.account.account.operations.interfaces.response.AccountCreateResponseInterface;

/**
 * Created by AKS on 10/7/2017.
 */

public interface AccountCreateInterface {
    void createAccount(String email, String token, String username) throws Exception;

    void setAccountCreateResponseInterface(AccountCreateResponseInterface accountInteractorInterface);

}
