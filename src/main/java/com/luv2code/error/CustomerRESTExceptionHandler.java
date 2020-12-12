package com.luv2code.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class CustomerRESTExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException exc) {
        CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("error-UUID", UUID.randomUUID().toString());
        headers.add("error-code", "CUSTOMER_NOT_FOUND");
        ResponseEntity<CustomerErrorResponse> responseEntity = new ResponseEntity<>(customerErrorResponse, headers, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> handleException(RuntimeException exc) {
        CustomerErrorResponse customerErrorResponse = new CustomerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), System.currentTimeMillis());
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("error-UUID", UUID.randomUUID().toString());
        headers.add("error-code", "CUSTOMER_NOT_FOUND");
        return new ResponseEntity<>(customerErrorResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
