package com.underpressure.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.underpressure.backend.controllers.classes.abstracts.GetController;
import com.underpressure.backend.controllers.classes.request.params.GetSubjectsParams;
import com.underpressure.backend.controllers.helpers.Fetch;

@RestController
public class FetchSubjectsController extends GetController<List<String>, GetSubjectsParams> {

    @Autowired
    Fetch.DB fetchDB;

    @Override
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> handle(@ModelAttribute GetSubjectsParams params) {

        return new ResponseEntity<>(fetchDB.subjects(jdbcTemplate), HttpStatus.OK);

    }

}
