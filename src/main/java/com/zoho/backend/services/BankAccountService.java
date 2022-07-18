package com.zoho.backend.services;

import com.plaid.client.model.AccountBalance;
import com.plaid.client.model.AccountBase;
import com.zoho.backend.classes.accounting.AddBankAccountInput;
import com.zoho.backend.model.BankAccount;
import com.zoho.backend.model.User;
import com.zoho.backend.provider.PlaidProvider;
import com.zoho.backend.repository.BankAccountRepository;
import com.zoho.backend.repository.UserRepository;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service("bankAccountService")
public class BankAccountService {

    @Autowired
    PlaidProvider plaidProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public ResponseEntity addBankAccount (AddBankAccountInput bankAccountInput) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accessToken = plaidProvider.getAccessToken(bankAccountInput.getPublicToken());
        User user = userRepository.getById(Long.parseLong(userDetails.getUsername()));
        AccountBase accountDetails = plaidProvider.getAccountBalance(bankAccountInput.accountId, accessToken);

        AccountBalance accountBalance = accountDetails.getBalances();

        BankAccount bankAccount = new BankAccount();
        bankAccount.setPlaidAccessToken(accessToken);
        bankAccount.setAccountName(bankAccountInput.accountName);
        bankAccount.setPlaidAccountName(accountDetails.getName());
        bankAccount.setPlaidAccountId(bankAccountInput.getAccountId());
        bankAccount.setNotes(bankAccount.getNotes());
        bankAccount.setAccountNumberMask(accountDetails.getMask());
        bankAccount.setStartingBalance(accountBalance.getCurrent().toString());
        bankAccount.setUser(user);
        bankAccount.setPlaidInstitutionId(bankAccountInput.getInstitutionId());

        logger.info("The Account Details", bankAccount);

        bankAccountRepository.save(bankAccount);

        HashMap<String, String> responseBody = new HashMap<>();

        responseBody.put("message", "Bank Account Added Successfully");
        responseBody.put("status", "SUCCESS");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);

    }

    @Transactional
    public ResponseEntity getAllBankAccounts() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getById(Long.parseLong(userDetails.getUsername()));
        List<BankAccount> accountList = bankAccountRepository.findByUser(user);
        return ResponseEntity.ok(accountList);
    }
}
