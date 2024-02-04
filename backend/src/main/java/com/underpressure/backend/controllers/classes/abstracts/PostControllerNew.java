package com.underpressure.backend.controllers.classes.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.underpressure.backend.controllers.classes.ApiResponse;

public abstract class PostControllerNew<S, T> extends Controller {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<ApiResponse<S>> handle(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody T requestData);

}
