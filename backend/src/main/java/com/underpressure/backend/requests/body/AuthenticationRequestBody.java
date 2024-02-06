package com.underpressure.backend.requests.body;

public class AuthenticationRequestBody {
    private String code;

    public AuthenticationRequestBody() {
    }

    public AuthenticationRequestBody(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
