package com.underpressure.backend.endpoints.classes.request.data;

import java.sql.Date;

public class EntryData {
    private Date createdAt;
    private Integer stressLevel;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }
}