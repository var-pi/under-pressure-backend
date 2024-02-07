package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.body.FollowSubjectRequestBody;
import com.underpressure.backend.requests.path_variables.FollowSubjectPathVariables;

public class FollowSubjectRequestData {
    private String subjectName;

    public FollowSubjectRequestData() {
    }

    public FollowSubjectRequestData(
            FollowSubjectRequestBody requestBody,
            FollowSubjectPathVariables pathVariables) {
        this.subjectName = pathVariables.getSubjectName();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
