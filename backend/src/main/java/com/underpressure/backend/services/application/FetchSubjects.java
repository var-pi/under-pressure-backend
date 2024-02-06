package com.underpressure.backend.services.application;

import java.util.List;

import com.underpressure.backend.services.database.DatabaseService;

class FetchSubjects {

    DatabaseService databaseService;

    public FetchSubjects(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    List<String> handle() {
        return databaseService.fetch().subjects();
    }

}
