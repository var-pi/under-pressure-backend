package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostController;
import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class UpdateEntryController extends AuthenticatedPostController<String, UpdateEntryRequestBody> {

    ApplicationService applicationService;

    public UpdateEntryController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/personal/entries/add")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody UpdateEntryRequestBody requestData) {

        applicationService.updateEntry(bearerToken, requestData);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
