package com.underpressure.backend.controllers.classes.request.body;

public class GetEntriesRequestBody {
    String userId;
    String subjectName;

    public GetEntriesRequestBody(String userId, String subjectName) {
        this.userId = userId;
        this.subjectName = subjectName;
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

}
