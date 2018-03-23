package com.instanect.accountcommon.account;

/**
 * Used by AppAccount
 */
public interface AppAccountInterface {

    void onAccountDetailsRetrieved(AppAccount appAccount);

    void onAccountRetrievalFailure();

    void onAccountAuthenticationFailure();
}
