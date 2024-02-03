package com.underpressure.backend.controllers.classes.request.body;

public class GetEntriesRequestBody {
    Integer userId;
    String subjectName;

    public GetEntriesRequestBody(Integer userId, String subjectName) {
        this.userId = userId;
        this.subjectName = subjectName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
