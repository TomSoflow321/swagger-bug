package com.tomsoflow.swagger.bug.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

public interface TestApi {
    String URL = "/test-api";

    @ApiOperation(value = "Represent bugged api", response = String.class)
    @ApiResponses(@ApiResponse(code = 200, message = "Operation successful", response = String.class))
    @RequestMapping(value = URL, produces = APPLICATION_JSON_VALUE, method = GET)
    @PreAuthorize("hasAuthority('myRole')")
    ResponseEntity<String> getSomeValue();
}
