package com.underpressure.backend.services.application;

import com.underpressure.backend.requests.data.UpdateEntryRequestData;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

public class UpdateEntry {

    UtilityService utilityService;
    DatabaseService databaseService;
    GoogleService googleService;
    String clientId;

    public UpdateEntry(
            UtilityService utilityService,
            DatabaseService databaseService,
            GoogleService googleService,
            String clientId) {
        this.utilityService = utilityService;
        this.databaseService = databaseService;
        this.googleService = googleService;
        this.clientId = clientId;
    }

    void handle(String bearerToken, UpdateEntryRequestData requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer stressLevel = requestData.getStressLevel();
        databaseService.validate().stressLevel(stressLevel);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        String subjectUuid = databaseService.fetch().subjectUuid(subjectName);
        Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectUuid);

        databaseService.validate().isFollowed(subjectInstanceId);

        if (databaseService.check().entryExists(subjectInstanceId)) {

            Integer entryId = databaseService.fetch().todaysEntryId(subjectInstanceId);

            databaseService.update().entry(entryId, stressLevel);

        } else {

            databaseService.add().entry(subjectInstanceId, stressLevel);

        }

    }

}
