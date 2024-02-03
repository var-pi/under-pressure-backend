package com.underpressure.backend.controllers.classes.request.body;

public class AuthenticateBody {
    private String code;

    public AuthenticateBody() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
