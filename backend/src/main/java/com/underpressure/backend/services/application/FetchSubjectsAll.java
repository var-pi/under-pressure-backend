package com.underpressure.backend.services.application;

import java.util.List;

import com.underpressure.backend.services.database.DatabaseService;

class FetchSubjectsAll {

    DatabaseService databaseService;

    public FetchSubjectsAll(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    List<String> handle() {
        return databaseService.fetch().subjects();
    }

}
