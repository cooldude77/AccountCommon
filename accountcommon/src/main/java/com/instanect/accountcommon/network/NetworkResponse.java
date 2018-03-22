package com.instanect.accountcommon.network;

/**
 * Could be a json object, json array or string
 * Returned by the server on network call
 */

public class NetworkResponse<T> implements NetworkResponseInterface{

    private T response;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
