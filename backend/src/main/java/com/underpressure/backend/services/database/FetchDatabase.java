package com.underpressure.backend.services.database;

import java.util.List;

import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.responses.EntryDataDto;

public interface FetchDatabase {

    public List<String> subjects();

    public String subjectUuid(String subjectName) throws RequestException;

    public List<String> followedSubjects(Integer userId);

    public Integer subjectInstanceId(Integer userId, String subjectUuid) throws RequestException;

    public Integer todaysEntryId(Integer subjectInstanceId) throws RequestException;

    public List<EntryDataDto> entries(Integer subjectInstanceId);

}
