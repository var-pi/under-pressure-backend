package com.underpressure.backend.classes.request.body;

public class FollowSubjectRequestBody {
    private String subjectName;

    public FollowSubjectRequestBody() {
    }

    public FollowSubjectRequestBody(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
