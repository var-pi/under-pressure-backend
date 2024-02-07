package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.body.UpdateEntryRequestBody;

public class UpdateEntryRequestData extends UpdateEntryRequestBody {
    private String subjectName;

    public UpdateEntryRequestData() {
    }

    public UpdateEntryRequestData(String subjectName, Integer stressLevel) {
        super(stressLevel);
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
