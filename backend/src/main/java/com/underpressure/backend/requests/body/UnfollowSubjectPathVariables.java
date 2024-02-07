package com.underpressure.backend.requests.body;

public class UnfollowSubjectPathVariables {
    private String subjectName;

    public UnfollowSubjectPathVariables() {
    }

    public UnfollowSubjectPathVariables(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
