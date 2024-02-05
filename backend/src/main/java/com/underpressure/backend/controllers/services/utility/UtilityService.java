package com.underpressure.backend.controllers.services.utility;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    public Extract extract() {
        return new ExtractImpl();
    }

}
