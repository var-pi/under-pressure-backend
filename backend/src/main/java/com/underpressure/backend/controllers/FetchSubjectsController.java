package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.AuthenticatedGetController;
import com.underpressure.backend.exceptions.RequestException;
import com.underpressure.backend.requests.data.FetchSubjectsRequestData;
import com.underpressure.backend.requests.path_variables.FetchSubjectsPathVariables;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FetchSubjectsController
        extends AuthenticatedGetController<List<String>, FetchSubjectsPathVariables> {

    ApplicationService applicationService;

    public FetchSubjectsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> handle(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            FetchSubjectsPathVariables pathVariables) throws RequestException {

        List<String> subjects = applicationService.fetchSubjects(bearerToken,
                new FetchSubjectsRequestData(pathVariables));

        return new ResponseEntity<>(subjects, HttpStatus.OK);

    }

}
