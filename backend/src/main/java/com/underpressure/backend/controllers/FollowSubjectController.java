package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostControllerNew;
import com.underpressure.backend.requests.body.FollowSubjectRequestBody;
import com.underpressure.backend.requests.data.FollowSubjectRequestData;
import com.underpressure.backend.requests.path_variables.FollowSubjectPathVariables;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FollowSubjectController
        extends AuthenticatedPostControllerNew<String, FollowSubjectRequestBody, FollowSubjectPathVariables> {

    private ApplicationService applicationService;

    public FollowSubjectController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/subjects/{subjectName}")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FollowSubjectRequestBody requestData,
            FollowSubjectPathVariables pathVariables) {

        applicationService.followSubject(bearerToken, new FollowSubjectRequestData(requestData, pathVariables));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}