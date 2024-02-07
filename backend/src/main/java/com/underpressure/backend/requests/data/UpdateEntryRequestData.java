package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.requests.path_variables.UpdateEntryRequestPathVariables;

public class UpdateEntryRequestData {
    private String subjectName;
    private Integer stressLevel;

    public UpdateEntryRequestData(UpdateEntryRequestBody requestBody,
            UpdateEntryRequestPathVariables requestPathVariables) {
        this.subjectName = requestPathVariables.getSubjectName();
        this.stressLevel = requestBody.getStressLevel();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }

}
