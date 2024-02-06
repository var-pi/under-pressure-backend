package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedPostController;
import com.underpressure.backend.requests.body.FetchEntriesRequestBody;
import com.underpressure.backend.responses.EntryDataDto;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FetchEntriesController extends AuthenticatedPostController<List<EntryDataDto>, FetchEntriesRequestBody> {

    ApplicationService applicationService;

    public FetchEntriesController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/personal/entries")
    public ResponseEntity<List<EntryDataDto>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestBody FetchEntriesRequestBody requestData) {

        List<EntryDataDto> entries = applicationService.fetchEntries(bearerToken, requestData);

        return new ResponseEntity<>(entries, HttpStatus.OK);

    }

}
