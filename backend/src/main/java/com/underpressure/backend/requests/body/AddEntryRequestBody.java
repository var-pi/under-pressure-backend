package com.underpressure.backend.requests.body;

public class AddEntryRequestBody {
    private String subjectName;
    private Integer stressLevel;

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
