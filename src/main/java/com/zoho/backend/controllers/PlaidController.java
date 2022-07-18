package com.zoho.backend.controllers;

import com.zoho.backend.services.PlaidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/plaid")
public class PlaidController {
    private static final Logger logger = LoggerFactory.getLogger(PlaidService.class);

    @Autowired
    PlaidService plaidService;

    @GetMapping("/link_token")
    public ResponseEntity get_link_token() throws IOException {
        return plaidService.getLinkToken();
    }
}
