package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.path_variables.UnfollowSubjectRequestPathVariables;

public class UnfollowSubjectRequestData {
    private String subjectName;

    public UnfollowSubjectRequestData(UnfollowSubjectRequestPathVariables pathVariables) {
        this.subjectName = pathVariables.getSubjectName();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
