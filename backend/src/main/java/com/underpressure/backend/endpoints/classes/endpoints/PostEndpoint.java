package com.underpressure.backend.endpoints.classes.endpoints;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class PostEndpoint extends Endpoint {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract Map<String, Object> handle(@RequestBody Map<String, Object> requestData);

}
