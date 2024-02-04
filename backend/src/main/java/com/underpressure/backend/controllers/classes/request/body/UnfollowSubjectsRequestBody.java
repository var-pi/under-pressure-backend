package com.underpressure.backend.controllers.classes.request.body;

public class UnfollowSubjectsRequestBody {
    private String subjectName;

    public UnfollowSubjectsRequestBody(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
