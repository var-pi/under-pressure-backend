package com.underpressure.backend.controllers.classes.abstracts;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.underpressure.backend.controllers.classes.ApiResponse;

public abstract class PostController<T> extends Controller {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<ApiResponse<T>> handle(@RequestBody Map<String, Object> requestData);

}
