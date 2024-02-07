package com.underpressure.backend.requests.data;

public class FetchEntriesRequestData {
    private String subjectName;

    public FetchEntriesRequestData() {
    }

    public FetchEntriesRequestData(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}