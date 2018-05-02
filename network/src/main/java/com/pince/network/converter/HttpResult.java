package com.pince.network.converter;

public class HttpResult<T> {

    private int code;

    private String message;

    private T data;

    public int getCode() {
        return code;
    }

    public HttpResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HttpResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public HttpResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
