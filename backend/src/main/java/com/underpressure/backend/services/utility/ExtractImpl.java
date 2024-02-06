package com.underpressure.backend.services.utility;

import org.springframework.stereotype.Component;

@Component
class ExtractImpl implements Extract {

    @Override
    public String token(String bearerToken) {
        String[] tokenParts = bearerToken.split("\\s+");

        if (tokenParts.length == 2 && "Bearer".equals(tokenParts[0])) {
            return tokenParts[1];
        } else {
            // Handle invalid or unexpected Authorization header format
            throw new IllegalArgumentException("Invalid Authorization header format");
        }
    }

}
