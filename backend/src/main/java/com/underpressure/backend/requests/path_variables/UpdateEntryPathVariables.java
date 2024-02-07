package com.underpressure.backend.requests.path_variables;

public class UpdateEntryPathVariables {
    String subjectName;

    public UpdateEntryPathVariables() {
    }

    public UpdateEntryPathVariables(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
