package com.underpressure.backend.requests.path_variables;

public class UpdateEntryRequestPathVariables {
    String subjectName;

    public UpdateEntryRequestPathVariables() {
    }

    public UpdateEntryRequestPathVariables(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
