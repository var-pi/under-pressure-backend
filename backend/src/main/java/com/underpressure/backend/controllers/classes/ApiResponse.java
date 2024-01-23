package com.underpressure.backend.controllers.classes;

public class ApiResponse<T> {

    private String status;
    private T data;
    private String message;

    public ApiResponse(boolean isSucess, T data, String message) {
        this.status = isSucess ? "success" : "fail";
        this.data = data;
        this.message = message;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
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
