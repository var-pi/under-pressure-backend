package com.underpressure.backend.responses;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EntryDataDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date creationDate;
    private Integer stressLevel;

    public EntryDataDto() {
    }

    public EntryDataDto(Date creationDate, Integer stressLevel) {
        this.creationDate = creationDate;
        this.stressLevel = stressLevel;
    }

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