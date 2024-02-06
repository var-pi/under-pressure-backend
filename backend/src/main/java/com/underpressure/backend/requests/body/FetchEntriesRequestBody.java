package com.underpressure.backend.requests.body;

public class FetchEntriesRequestBody {
    private String subjectName;

    public FetchEntriesRequestBody() {
    }

    public FetchEntriesRequestBody(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
