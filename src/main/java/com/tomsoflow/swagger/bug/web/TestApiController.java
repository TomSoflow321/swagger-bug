package com.tomsoflow.swagger.bug.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController implements TestApi {

    @Override
    public ResponseEntity<String> getSomeValue() {
        return ResponseEntity.ok("{\"result\":\"value\"}");
    }
}
