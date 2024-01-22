package com.underpressure.backend.endpoints.classes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.underpressure.backend.endpoints.classes.ApiResponse;

public abstract class GetEndpoint<T> extends Endpoint {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    // public abstract Map<String, Object> handle();
    public abstract ResponseEntity<ApiResponse<T>> handle();

}
