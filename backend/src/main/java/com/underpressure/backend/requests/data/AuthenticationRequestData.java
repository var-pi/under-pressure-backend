package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.body.AuthenticationRequestBody;

public class AuthenticationRequestData extends AuthenticationRequestBody {

    public AuthenticationRequestData() {
        super();
    }

    public AuthenticationRequestData(String code) {
        super(code);
    }

}
