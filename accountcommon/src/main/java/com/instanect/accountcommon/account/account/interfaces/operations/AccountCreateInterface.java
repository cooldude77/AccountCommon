package com.instanect.accountcommon.account.account.interfaces.operations;

/**
 * Created by AKS on 10/7/2017.
 */

public interface AccountCreateInterface {
    void createAccount(String email, String token, String username,String appAuthority) throws Exception;

}
