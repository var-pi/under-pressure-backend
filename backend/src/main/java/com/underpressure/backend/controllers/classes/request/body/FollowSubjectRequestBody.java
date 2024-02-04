package com.underpressure.backend.controllers.classes.request.body;

public class FollowSubjectRequestBody {
    private String idTokenString;
    private String subjectName;

    public FollowSubjectRequestBody() {
    }

    public FollowSubjectRequestBody(String idTokenString, String subjectName) {
        this.idTokenString = idTokenString;
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getIdTokenString() {
        return idTokenString;
    }

    public void setIdTokenString(String idToken) {
        this.idTokenString = idToken;
    }

}
