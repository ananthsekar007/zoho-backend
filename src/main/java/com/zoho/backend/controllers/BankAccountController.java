package com.zoho.backend.controllers;

import com.zoho.backend.classes.accounting.AddBankAccountInput;
import com.zoho.backend.model.BankAccount;
import com.zoho.backend.services.BankAccountService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankaccount")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;


@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addBankAccount(@RequestBody AddBankAccountInput bankAccountInput) throws Exception{
    return bankAccountService.addBankAccount(bankAccountInput);
}

@GetMapping(value = "/get-all")
    public ResponseEntity getAllBankAccounts() {
    return bankAccountService.getAllBankAccounts();
}
}
