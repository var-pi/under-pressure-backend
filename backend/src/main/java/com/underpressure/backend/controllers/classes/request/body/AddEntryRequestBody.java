package com.underpressure.backend.controllers.classes.request.body;

public class AddEntryRequestBody {
    String userId;
    String subjectName;
    Integer stressLevel;

    public AddEntryRequestBody(String userId, String subjectName, Integer stressLevel) {
        this.userId = userId;
        this.subjectName = subjectName;
        this.stressLevel = stressLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
