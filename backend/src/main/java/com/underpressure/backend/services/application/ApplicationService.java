package com.underpressure.backend.services.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.underpressure.backend.services.database.DatabaseService;

@Service
public class ApplicationService {

    @Autowired
    DatabaseService databaseService;

    public List<String> fetchSubjects() {
        return (new FetchSubjects(databaseService)).handle();
    }

}
