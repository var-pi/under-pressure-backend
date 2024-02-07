package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.GetController;
import com.underpressure.backend.requests.params.FetchSubjectsAllPathVariables;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FetchSubjectsAllController extends GetController<List<String>, FetchSubjectsAllPathVariables> {

    ApplicationService applicationService;

    public FetchSubjectsAllController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @GetMapping("/subjects/all")
    public ResponseEntity<List<String>> handle(FetchSubjectsAllPathVariables params) {

        return new ResponseEntity<>(applicationService.fetchSubjects(), HttpStatus.OK);

    }

}
