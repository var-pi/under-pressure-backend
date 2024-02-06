package com.underpressure.backend.services.database;

import java.util.List;

import com.underpressure.backend.classes.dto.EntryDataDto;
import com.underpressure.backend.exceptions.RequestException;

public interface FetchDatabase {

    public List<String> subjects();

    public Integer subjectId(String subjectName) throws RequestException;

    public List<String> followedSubjects(Integer userId);

    public Integer subjectInstanceId(Integer userId, Integer subjectId) throws RequestException;

    public Integer todaysEntryId(Integer subjectInstanceId) throws RequestException;

    public List<EntryDataDto> entries(Integer subjectInstanceId);

}
