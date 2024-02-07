package com.underpressure.backend.requests.path_variables;

public class FollowSubjectPathVariables {

    private String subjectName;

    public FollowSubjectPathVariables() {
    }

    public FollowSubjectPathVariables(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
