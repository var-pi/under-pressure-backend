package com.underpressure.backend.services.application;

import java.util.List;

import com.underpressure.backend.requests.body.FetchFollowedSubjectsRequestBody;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

public class FetchFollowedSubjects {

    UtilityService utilityService;
    GoogleService googleService;
    DatabaseService databaseService;
    String clientId;

    public FetchFollowedSubjects(
            UtilityService utilityService,
            GoogleService googleService,
            DatabaseService databaseService,
            String clientId) {
        this.utilityService = utilityService;
        this.googleService = googleService;
        this.databaseService = databaseService;
        this.clientId = clientId;
    }

    List<String> handle(String bearerToken, FetchFollowedSubjectsRequestBody requestData) {
        databaseService.validate().bearerToken(bearerToken);
        String idTokenString = utilityService.extract().token(bearerToken);

        Integer userId = googleService.fetch().userId(idTokenString, clientId);

        return databaseService.fetch().followedSubjects(userId);
    }

}
