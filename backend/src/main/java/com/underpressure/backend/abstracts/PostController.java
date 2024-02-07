package com.underpressure.backend.abstracts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class PostController<S, T, V> extends Controller {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract ResponseEntity<S> handle(@RequestBody T requestBody, V pathVariables);

}
