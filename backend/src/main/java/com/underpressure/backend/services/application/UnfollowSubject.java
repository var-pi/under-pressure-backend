package com.underpressure.backend.services.application;

import com.underpressure.backend.requests.data.UnfollowSubjectRequestData;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

public class UnfollowSubject {

    UtilityService utilityService;
    DatabaseService databaseService;
    GoogleService googleService;
    String clientId;

    public UnfollowSubject(
            UtilityService utilityService,
            DatabaseService databaseService,
            GoogleService googleService,
            String clientId) {
        this.utilityService = utilityService;
        this.databaseService = databaseService;
        this.googleService = googleService;
        this.clientId = clientId;
    }

    void handle(String bearerToken, UnfollowSubjectRequestData requestData) {

        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        String subjectName = requestData.getSubjectName();
        databaseService.validate().subjectName(subjectName);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        String subjectUuid = databaseService.fetch().subjectUuid(subjectName);
        Integer subjectInstanceId = databaseService.fetch().subjectInstanceId(userId, subjectUuid);

        databaseService.validate().isFollowed(subjectInstanceId);

        databaseService.set().toNotFollow(subjectInstanceId);

    }

}
