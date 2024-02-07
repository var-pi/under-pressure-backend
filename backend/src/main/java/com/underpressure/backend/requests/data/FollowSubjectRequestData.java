package com.underpressure.backend.requests.data;

import com.underpressure.backend.requests.body.FollowSubjectRequestBody;

public class FollowSubjectRequestData extends FollowSubjectRequestBody {
    private String subjectName;

    public FollowSubjectRequestData() {
        super();
    }

    public FollowSubjectRequestData(String subjectName) {
        super();
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
