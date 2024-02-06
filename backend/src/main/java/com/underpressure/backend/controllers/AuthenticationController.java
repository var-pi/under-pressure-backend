package com.underpressure.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.abstracts.PostController;
import com.underpressure.backend.requests.body.AuthenticationRequestBody;
import com.underpressure.backend.services.application.ApplicationService;

@RestController
public class AuthenticationController extends PostController<String, AuthenticationRequestBody> {

    ApplicationService applicationService;

    public AuthenticationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping("/auth")
    public ResponseEntity<String> handle(@RequestBody AuthenticationRequestBody requestData) {

        String idTokenString = applicationService.authenticate(requestData);

        return new ResponseEntity<>(idTokenString, HttpStatus.OK);

    }

}
