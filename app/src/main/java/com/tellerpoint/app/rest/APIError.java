package com.tellerpoint.app.rest;

/**
 * Created by eit on 1/27/16.
 */
public class APIError {
    private int statusCode;
    private String message;
    private String error;

    public APIError() {
    }

    public APIError(int statusCode, String message, String error){
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String message() {
        return message;
    }

    public String error() {
        return error;
    }
}