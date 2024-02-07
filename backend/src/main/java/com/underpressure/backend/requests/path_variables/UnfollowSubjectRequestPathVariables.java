package com.underpressure.backend.requests.path_variables;

public class UnfollowSubjectRequestPathVariables {
    private String subjectName;

    public UnfollowSubjectRequestPathVariables() {
    }

    public UnfollowSubjectRequestPathVariables(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
