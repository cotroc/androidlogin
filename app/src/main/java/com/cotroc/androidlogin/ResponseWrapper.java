package com.cotroc.androidlogin;

/**
 * Created by Cotroc on 25/08/2017.
 */

public class ResponseWrapper {

    private boolean succesStatus;
    private String message;
    private Object response;

    public ResponseWrapper() {

    }
    public boolean isSuccesStatus() {
        return succesStatus;
    }
    public void setSuccesStatus(boolean succesStatus) {
        this.succesStatus = succesStatus;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getResponse() {
        return response;
    }
    public void setResponse(Object response) {
        this.response = response;
    }
}