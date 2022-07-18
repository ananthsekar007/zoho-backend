package com.zoho.backend.services;

import com.zoho.backend.provider.PlaidProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;

@Service("plaidService")
public class PlaidService {

    @Autowired
    PlaidProvider plaidProvider;

    private static final Logger logger = LoggerFactory.getLogger(PlaidService.class);

    @Transactional
    public ResponseEntity getLinkToken () throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String plaidLinkToken = plaidProvider.createLinkToken(userDetails.getUsername());
        HashMap<String, String> response = new HashMap<>();
        response.put("link_token", plaidLinkToken);
        return ResponseEntity.ok(response);
    }
}
