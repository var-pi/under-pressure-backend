package com.underpressure.backend.controllers.classes.request.body;

public class CreateUserRequestBody {
    String userId;

    public CreateUserRequestBody(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
