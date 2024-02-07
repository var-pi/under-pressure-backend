package com.underpressure.backend.requests.body;

import com.underpressure.backend.requests.data.UpdateEntryRequestData;

public class UpdateEntryRequestBody {

    private Integer stressLevel;

    public UpdateEntryRequestBody() {
    }

    public UpdateEntryRequestBody(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }

    public UpdateEntryRequestBody(UpdateEntryRequestData requestData) {
        this.stressLevel = requestData.getStressLevel();
    }

    public Integer getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }

}
