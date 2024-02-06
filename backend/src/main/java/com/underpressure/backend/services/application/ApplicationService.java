package com.underpressure.backend.services.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@Service
public class ApplicationService {

    private UtilityService utilityService;

    private GoogleService googleService;

    private DatabaseService databaseService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    public ApplicationService(UtilityService utilityService, GoogleService googleService,
            DatabaseService databaseService) {
        this.utilityService = utilityService;
        this.googleService = googleService;
        this.databaseService = databaseService;
    }

    public List<String> fetchSubjects() {
        return (new FetchSubjects(databaseService)).handle();
    }

    public List<String> fetchFollowedSubjects(String bearerToken) {
        return (new FetchFollowedSubjects(utilityService, googleService, databaseService, clientId))
                .handle(bearerToken);
    }

}
