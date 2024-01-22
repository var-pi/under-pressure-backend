package com.underpressure.backend.controllers.classes.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.underpressure.backend.controllers.classes.ApiResponse;

public abstract class GetController<T> extends Controller {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    // public abstract Map<String, Object> handle();
    public abstract ResponseEntity<ApiResponse<T>> handle();

}
