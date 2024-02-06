package com.underpressure.backend.controllers.classes.abstracts;

import org.springframework.beans.factory.annotation.Value;

abstract class AuthenticatedController {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    protected String clientId;

}
