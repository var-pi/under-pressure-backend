package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.GetController;
import com.underpressure.backend.controllers.helpers.Get;

@RestController
public class SubjectsController extends GetController<List<String>> {

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
