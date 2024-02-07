package com.underpressure.backend.services.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.underpressure.backend.requests.body.AuthenticationRequestBody;
import com.underpressure.backend.requests.body.FetchEntriesPathVariables;
import com.underpressure.backend.requests.body.FetchSubjectsPathVariables;
import com.underpressure.backend.requests.body.FollowSubjectRequestBody;
import com.underpressure.backend.requests.body.UnfollowSubjectPathVariables;
import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.responses.EntryDataDto;
import com.underpressure.backend.services.database.DatabaseService;
import com.underpressure.backend.services.google.GoogleService;
import com.underpressure.backend.services.utility.UtilityService;

@Service
public class ApplicationService {

    private UtilityService utilityService;
    private GoogleService googleService;
    private DatabaseService databaseService;

    public ApplicationService(UtilityService utilityService, GoogleService googleService,
            DatabaseService databaseService) {
        this.utilityService = utilityService;
        this.googleService = googleService;
        this.databaseService = databaseService;
    }

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    public List<String> fetchSubjects() {
        return new FetchSubjectsAll(databaseService)
                .handle();
    }

    public List<String> fetchSubjects(String bearerToken, FetchSubjectsPathVariables requestData) {
        return new FetchFollowedSubjects(utilityService, googleService, databaseService, clientId)
                .handle(bearerToken, requestData);
    }

    public void followSubject(String bearerToken, FollowSubjectRequestBody requestData) {
        new FollowSubject(utilityService, databaseService, googleService, clientId)
                .handle(bearerToken, requestData);
    }

    public void unfollowSubject(String bearerToken, UnfollowSubjectPathVariables requestData) {
        new UnfollowSubject(utilityService, databaseService, googleService, clientId)
                .handle(bearerToken, requestData);
    }

    public List<EntryDataDto> fetchEntries(String bearerToken, FetchEntriesPathVariables requestData) {
        return new FetchEntries(utilityService, googleService, databaseService, clientId)
                .handle(bearerToken, requestData);
    }

    public void updateEntry(String bearerToken, UpdateEntryRequestBody requestData) {
        new UpdateEntry(utilityService, databaseService, googleService, clientId)
                .handle(bearerToken, requestData);
    }

    public String authenticate(AuthenticationRequestBody requestData) {
        return new Authentication(googleService, databaseService, tokenUri, redirectUri, clientId, clientSecret)
                .handle(requestData);
    }

}
