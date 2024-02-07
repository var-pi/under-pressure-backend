package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedGetController;
import com.underpressure.backend.requests.body.FetchEntriesPathVariables;
import com.underpressure.backend.responses.EntryDataDto;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FetchEntriesController extends AuthenticatedGetController<List<EntryDataDto>, FetchEntriesPathVariables> {

    ApplicationService applicationService;

    public FetchEntriesController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @GetMapping("/subjects/{subjectName}/entries")
    public ResponseEntity<List<EntryDataDto>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            FetchEntriesPathVariables requestData) {

        List<EntryDataDto> entries = applicationService.fetchEntries(bearerToken, requestData);

        return new ResponseEntity<>(entries, HttpStatus.OK);

    }

}
