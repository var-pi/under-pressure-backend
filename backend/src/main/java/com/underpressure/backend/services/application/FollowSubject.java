package com.underpressure.backend.services.application;

import com.underpressure.backend.requests.body.FollowSubjectRequestBody;
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

    void handle(String bearerToken, FollowSubjectRequestBody requestData) {
        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        Integer subjectId = databaseService.fetch().subjectId(subjectName);

        if (databaseService.check().subjectInstanceExists(userId, subjectId)) {

            Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectId);

            databaseService.validate().isUnfollowed(subjectInstanceId);

            databaseService.set().toFollow(subjectInstanceId);

        } else {

            databaseService.add().subjectInstance(userId, subjectId);

        }

    }

}
