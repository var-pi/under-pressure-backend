package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.ApiResponse;
import com.underpressure.backend.controllers.classes.abstracts.GetController;
import com.underpressure.backend.controllers.classes.request.params.GetSubjectsParams;
import com.underpressure.backend.controllers.helpers.Get;

@RestController
public class GetSubjectsController extends GetController<List<String>, GetSubjectsParams> {

    @Override
    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<List<String>>> handle(@ModelAttribute GetSubjectsParams params) {

        return new ResponseEntity<>(
                new ApiResponse<>(true, Get.subjects(jdbcTemplate), null),
                HttpStatus.OK);

    }

}
