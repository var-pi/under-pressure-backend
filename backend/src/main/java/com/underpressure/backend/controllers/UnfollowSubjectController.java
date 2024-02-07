package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedDeleteController;
import com.underpressure.backend.requests.data.UnfollowSubjectRequestData;
import com.underpressure.backend.requests.path_variables.UnfollowSubjectRequestPathVariables;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class UnfollowSubjectController
        extends AuthenticatedDeleteController<String, UnfollowSubjectRequestPathVariables> {

    ApplicationService applicationService;

    public UnfollowSubjectController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @DeleteMapping("/subjects/{subjectName}")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            UnfollowSubjectRequestPathVariables pathVariables) {

        applicationService.unfollowSubject(bearerToken, new UnfollowSubjectRequestData(pathVariables));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
