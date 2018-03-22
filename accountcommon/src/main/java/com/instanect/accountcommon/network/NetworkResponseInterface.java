package com.instanect.accountcommon.network;

/**
 * Created by AKS on 3/11/2018.
 */

public interface NetworkResponseInterface<T> {

    T getResponse();

    void setResponse(T response);

}
