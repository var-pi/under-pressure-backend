package com.underpressure.backend.requests.path_variables;

public class FetchEntriesPathVariables {

    private String subjectName;

    public FetchEntriesPathVariables() {
    }

    public FetchEntriesPathVariables(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
