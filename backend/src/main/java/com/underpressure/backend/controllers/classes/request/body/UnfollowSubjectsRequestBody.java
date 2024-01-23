package com.underpressure.backend.controllers.classes.request.body;

public class UnfollowSubjectsRequestBody {
    private String userId;
    private String subjectName;

    public UnfollowSubjectsRequestBody(String userId, String subjectName) {
        this.userId = userId;
        this.subjectName = subjectName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
