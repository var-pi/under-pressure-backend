package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedDeleteController;
import com.underpressure.backend.requests.body.UnfollowSubjectPathVariables;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class UnfollowSubjectController extends AuthenticatedDeleteController<String, UnfollowSubjectPathVariables> {

    ApplicationService applicationService;

    public UnfollowSubjectController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @DeleteMapping("/subjects/{subjectName}")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            UnfollowSubjectPathVariables requestData) {

        applicationService.unfollowSubject(bearerToken, requestData);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
