package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.GetController;
import com.underpressure.backend.requests.params.GetSubjectsParams;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class FetchSubjectsController extends GetController<List<String>, GetSubjectsParams> {

    ApplicationService applicationService;

    public FetchSubjectsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> handle(@ModelAttribute GetSubjectsParams params) {

        return new ResponseEntity<>(applicationService.fetchSubjects(), HttpStatus.OK);

    }

}
