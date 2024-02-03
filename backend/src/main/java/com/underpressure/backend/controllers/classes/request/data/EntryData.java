package com.underpressure.backend.controllers.classes.request.data;

import java.sql.Date;

public class EntryData {
    private Date creationDate;
    private Integer stressLevel;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }
}