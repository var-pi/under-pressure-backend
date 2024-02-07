package com.underpressure.backend.requests.body;

import com.underpressure.backend.requests.data.AuthenticationRequestData;

public class AuthenticationRequestBody {

    private String code;

    public AuthenticationRequestBody() {
    }

    public AuthenticationRequestBody(String code) {
        this.code = code;
    }

    public AuthenticationRequestBody(AuthenticationRequestData requestData) {
        this.code = requestData.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
