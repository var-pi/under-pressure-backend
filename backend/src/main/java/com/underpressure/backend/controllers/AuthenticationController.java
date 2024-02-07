package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.PostController;
import com.underpressure.backend.requests.data.AuthenticationRequestData;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class AuthenticationController extends PostController<String, AuthenticationRequestData> {

    ApplicationService applicationService;

    public AuthenticationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/auth")
    public ResponseEntity<String> handle(AuthenticationRequestData requestData) {

        String idTokenString = applicationService.authenticate(requestData);

        return new ResponseEntity<>(idTokenString, HttpStatus.OK);

    }

}
