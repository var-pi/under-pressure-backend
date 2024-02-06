package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedGetController;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.requests.body.FetchFollowedSubjectsRequestBody;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FetchFollowedSubjectsController
        extends AuthenticatedGetController<List<String>, FetchFollowedSubjectsRequestBody> {

    ApplicationService applicationService;

    public FetchFollowedSubjectsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @GetMapping("/personal/subjects")
    public ResponseEntity<List<String>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @ModelAttribute FetchFollowedSubjectsRequestBody requestData) throws RequestException {

        List<String> subjects = applicationService.fetchFollowedSubjects(bearerToken, requestData);

        return new ResponseEntity<>(subjects, HttpStatus.OK);

    }

}
