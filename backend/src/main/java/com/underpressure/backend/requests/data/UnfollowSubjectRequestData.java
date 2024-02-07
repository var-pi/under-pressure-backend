package com.underpressure.backend.requests.data;

public class UnfollowSubjectRequestData {
    private String subjectName;

    public UnfollowSubjectRequestData() {
    }

    public UnfollowSubjectRequestData(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
