package com.underpressure.backend.controllers.classes.request.body;

public class AuthenticationBody {
    private String code;

    public AuthenticationBody() {
    }

    public AuthenticationBody(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
