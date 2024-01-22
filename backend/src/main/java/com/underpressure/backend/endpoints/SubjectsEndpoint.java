package com.underpressure.backend.endpoints;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.endpoints.classes.ApiResponse;
import com.underpressure.backend.endpoints.classes.endpoints.GetEndpoint;
import com.underpressure.backend.endpoints.helpers.Get;

@RestController
public class SubjectsEndpoint extends GetEndpoint<List<String>> {

    @Override
    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<List<String>>> handle() {
        try {
            return new ResponseEntity<>(
                    new ApiResponse<>(true, Get.subjects(jdbcTemplate), null),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ApiResponse<>(false, null, e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
