package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostController;
import com.underpressure.backend.requests.body.UnfollowSubjectRequestBody;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class UnfollowSubjectController extends AuthenticatedPostController<String, UnfollowSubjectRequestBody> {

    ApplicationService applicationService;

    public UnfollowSubjectController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/personal/subjects/unfollow")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody UnfollowSubjectRequestBody requestData) {

        applicationService.unfollowSubject(bearerToken, requestData);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
