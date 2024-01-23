package com.underpressure.backend.controllers.classes.request.body;

public class FollowedSubjectsRequestBody {
    private String userId;

    public FollowedSubjectsRequestBody() {
    }

    public FollowedSubjectsRequestBody(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
