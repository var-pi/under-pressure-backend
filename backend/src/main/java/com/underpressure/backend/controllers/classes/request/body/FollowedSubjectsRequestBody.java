package com.underpressure.backend.controllers.classes.request.body;

public class FollowedSubjectsRequestBody {
    private Integer userId;

    public FollowedSubjectsRequestBody(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
