package com.underpressure.backend.services.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.underpressure.backend.requests.data.AuthenticationRequestData;
import com.underpressure.backend.requests.data.FetchEntriesRequestData;
import com.underpressure.backend.requests.data.FetchSubjectsAllRequestData;
import com.underpressure.backend.requests.data.FetchSubjectsRequestData;
import com.underpressure.backend.requests.data.FollowSubjectRequestData;
import com.underpressure.backend.requests.data.UnfollowSubjectRequestData;
import com.underpressure.backend.requests.data.UpdateEntryRequestData;
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

    public List<String> fetchSubjectsAll(FetchSubjectsAllRequestData requestData) {
        return new FetchSubjectsAll(databaseService)
                .handle(requestData);
    }

    public List<String> fetchSubjects(String bearerToken, FetchSubjectsRequestData requestData) {
        return new FetchFollowedSubjects(utilityService, googleService, databaseService, clientId)
                .handle(bearerToken, requestData);
    }

    public void followSubject(String bearerToken, FollowSubjectRequestData requestData) {
        new FollowSubject(utilityService, databaseService, googleService, clientId)
                .handle(bearerToken, requestData);
    }

    public void unfollowSubject(String bearerToken, UnfollowSubjectRequestData requestData) {
        new UnfollowSubject(utilityService, databaseService, googleService, clientId)
                .handle(bearerToken, requestData);
    }

    public List<EntryDataDto> fetchEntries(String bearerToken, FetchEntriesRequestData requestData) {
        return new FetchEntries(utilityService, googleService, databaseService, clientId)
                .handle(bearerToken, requestData);
    }

    public void updateEntry(String bearerToken, UpdateEntryRequestData requestData) {
        new UpdateEntry(utilityService, databaseService, googleService, clientId)
                .handle(bearerToken, requestData);
    }

    public String authenticate(AuthenticationRequestData requestData) {
        return new Authentication(googleService, databaseService, tokenUri, redirectUri, clientId, clientSecret)
                .handle(requestData);
    }

}
