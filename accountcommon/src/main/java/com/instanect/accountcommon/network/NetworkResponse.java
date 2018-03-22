package com.instanect.accountcommon.network;

/**
 * Could be a json object, json array or string
 * Returned by the server on network call
 */

public class NetworkResponse<T> implements NetworkResponseInterface<T> {

    private T response;

    @Override
    public T getResponse() {
        return response;
    }

    @Override
    public void setResponse(T response) {
        this.response = response;
    }
}
