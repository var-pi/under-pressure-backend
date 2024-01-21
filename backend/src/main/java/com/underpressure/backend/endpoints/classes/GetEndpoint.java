package com.underpressure.backend.endpoints.classes;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

public abstract class GetEndpoint extends Endpoint {

    @CrossOrigin(origins = "*") // TODO Change to an appropriate url
    public abstract Map<String, Object> handle();

}
