package com.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> helloAdmin() {

        return ResponseEntity.ok("Welcome Admin");
    }

    @RestController
    @RequestMapping("/api/customer")
    public class CustomerController {

        @GetMapping("/hello")
        @PreAuthorize("hasRole('CUSTOMER')")
        public ResponseEntity<String> helloCustomer() {

            return ResponseEntity.ok("Welcome Customer");
        }
    }
}