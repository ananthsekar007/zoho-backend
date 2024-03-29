package com.zoho.backend.repository;

import com.zoho.backend.model.BankAccount;
import com.zoho.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByUser(User user);

}
