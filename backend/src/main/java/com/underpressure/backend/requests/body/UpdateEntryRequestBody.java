package com.underpressure.backend.requests.body;

public class UpdateEntryRequestBody {

    private Integer stressLevel;

    public UpdateEntryRequestBody() {
    }

    public UpdateEntryRequestBody(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }

    public Integer getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }

}
