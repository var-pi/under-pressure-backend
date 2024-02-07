package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.body.AuthenticationRequestBody;
import com.underpressure.backend.requests.path_variables.AuthenticationPathVariables;

public class AuthenticationRequestData {

    private String code;

    public AuthenticationRequestData(AuthenticationRequestBody requestBody, AuthenticationPathVariables pathVariables) {
        this.code = requestBody.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
