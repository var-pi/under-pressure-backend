package com.underpressure.backend.services.application;

import java.util.List;

import com.underpressure.backend.requests.data.FetchEntriesRequestData;
import com.underpressure.backend.responses.EntryDataDto;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

public class FetchEntries {

    UtilityService utilityService;
    GoogleService googleService;
    DatabaseService databaseService;
    String clientId;

    public FetchEntries(
            UtilityService utilityService,
            GoogleService googleService,
            DatabaseService databaseService,
            String clientId) {
        this.utilityService = utilityService;
        this.googleService = googleService;
        this.databaseService = databaseService;
        this.clientId = clientId;
    }

    List<EntryDataDto> handle(String bearerToken, FetchEntriesRequestData requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        Integer subjectId = databaseService.fetch().subjectId(subjectName);
        Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectId);

        List<EntryDataDto> entries = databaseService.fetch().entries(subjectInstanceId);

        return entries;

    }

}
