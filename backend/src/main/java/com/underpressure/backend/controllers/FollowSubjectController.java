package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostController;
import com.underpressure.backend.requests.body.FollowSubjectRequestBody;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FollowSubjectController extends AuthenticatedPostController<String, FollowSubjectRequestBody> {

    private ApplicationService applicationService;

    public FollowSubjectController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/subjects")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FollowSubjectRequestBody requestData) {

        applicationService.followSubject(bearerToken, requestData);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}