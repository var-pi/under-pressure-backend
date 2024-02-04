package com.underpressure.backend.controllers.classes.request.body;

public class AddEntryRequestBody {
    String subjectName;
    Integer stressLevel;

    public AddEntryRequestBody() {
    }

    public AddEntryRequestBody(String subjectName, Integer stressLevel) {
        this.subjectName = subjectName;
        this.stressLevel = stressLevel;
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
