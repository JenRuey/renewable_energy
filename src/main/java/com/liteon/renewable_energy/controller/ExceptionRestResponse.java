package com.liteon.renewable_energy.controller;

public class ExceptionRestResponse {
    int code;
    String message;

    public ExceptionRestResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExceptionRestResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
