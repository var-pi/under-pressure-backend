package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.path_variables.UnfollowSubjectPathVariables;

public class UnfollowSubjectRequestData {
    private String subjectName;

    public UnfollowSubjectRequestData(UnfollowSubjectPathVariables pathVariables) {
        this.subjectName = pathVariables.getSubjectName();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
