package com.underpressure.backend.classes.request.body;

public class UnfollowSubjectRequestBody {
    private String subjectName;

    public UnfollowSubjectRequestBody() {
    }

    public UnfollowSubjectRequestBody(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
