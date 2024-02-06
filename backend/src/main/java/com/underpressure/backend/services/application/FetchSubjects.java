package com.underpressure.backend.services.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.underpressure.backend.services.database.DatabaseService;

@Component // TODO Do I need this annotation? (In all other files too)
class FetchSubjects {

    DatabaseService databaseService;

    public FetchSubjects(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    List<String> handle() {
        return databaseService.fetch().subjects();
    }

}
