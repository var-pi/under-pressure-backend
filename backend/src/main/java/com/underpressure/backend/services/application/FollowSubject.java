package com.underpressure.backend.services.application;

import com.underpressure.backend.requests.data.FollowSubjectRequestData;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

public class FollowSubject {

    UtilityService utilityService;
    DatabaseService databaseService;
    GoogleService googleService;
    String clientId;

    public FollowSubject(
            UtilityService utilityService,
            DatabaseService databaseService,
            GoogleService googleService,
            String clientId) {
        this.utilityService = utilityService;
        this.databaseService = databaseService;
        this.googleService = googleService;
        this.clientId = clientId;
    }

    void handle(String bearerToken, FollowSubjectRequestData requestData) {
        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        String subjectUuid = databaseService.fetch().subjectUuid(subjectName);

        if (databaseService.check().subjectInstanceExists(userId, subjectUuid)) {

            Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectUuid);

            databaseService.validate().isUnfollowed(subjectInstanceId);

            databaseService.set().toFollow(subjectInstanceId);

        } else {

            databaseService.add().subjectInstance(userId, subjectUuid);

        }

    }

}
