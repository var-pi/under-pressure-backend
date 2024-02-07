package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.path_variables.FetchEntriesPathVariables;

public class FetchEntriesRequestData {
    private String subjectName;

    public FetchEntriesRequestData(FetchEntriesPathVariables pathVariables) {
        this.subjectName = pathVariables.getSubjectName();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}