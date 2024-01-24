package com.underpressure.backend.controllers.classes.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.underpressure.backend.controllers.classes.ApiResponse;

public abstract class GetController<S, T> extends Controller {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<ApiResponse<S>> handle(@ModelAttribute T params);

}
