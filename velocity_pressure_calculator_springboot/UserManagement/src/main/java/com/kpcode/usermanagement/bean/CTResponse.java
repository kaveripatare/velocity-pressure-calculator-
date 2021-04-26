package com.kpcode.usermanagement.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Author kaveri
 * @create 10/04/21
 */
public class CTResponse<T> {

    private String message;
    private String status;
    @JsonProperty("data")
    private T responseData;

    public CTResponse() {
        super();
    }

    public CTResponse(String status, String message, T responseData) {
        super();
        this.status=status;
        this.message = message;
        this.responseData = responseData;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
