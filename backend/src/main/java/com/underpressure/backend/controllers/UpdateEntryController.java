package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostControllerNew;
import com.underpressure.backend.requests.body.UpdateEntryRequestBody;
import com.underpressure.backend.requests.data.UpdateEntryRequestData;
import com.underpressure.backend.requests.path_variables.UpdateEntryPathVariables;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class UpdateEntryController
        extends AuthenticatedPostControllerNew<String, UpdateEntryRequestBody, UpdateEntryPathVariables> {

    ApplicationService applicationService;

    public UpdateEntryController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/subjects/{subjectName}/entries")
    public ResponseEntity<String> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody UpdateEntryRequestBody requestBody,
            UpdateEntryPathVariables requestPathVariables) {

        applicationService.updateEntry(bearerToken, new UpdateEntryRequestData(requestBody, requestPathVariables));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
