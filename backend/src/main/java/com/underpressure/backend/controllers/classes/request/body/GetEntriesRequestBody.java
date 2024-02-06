package com.underpressure.backend.controllers.classes.request.body;

public class GetEntriesRequestBody {
    private String subjectName;

    public GetEntriesRequestBody() {
    }

    public GetEntriesRequestBody(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
