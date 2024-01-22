package com.underpressure.backend.endpoints.classes;

public class ApiResponse<T> {

    private boolean status;
    private T data;
    private String message;

    public ApiResponse(boolean status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
