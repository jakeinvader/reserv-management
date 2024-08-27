package com.api_demo.reserv_management.api.v1.local.api_reserv_management.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/local/api-reserv-management/test")
@CrossOrigin
public class TestController {

    @Value("${enviroment.current}")
    private String enviroment;

    @GetMapping("")
    public ResponseEntity<?> test(
            HttpServletRequest req
    )  {
        String message = "Backend de gestion de reservas ok. " + enviroment;
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }
}
