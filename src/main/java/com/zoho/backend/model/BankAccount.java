package com.zoho.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "bank_accounts")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class BankAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @JsonBackReference
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "plaid_account_id")
    private String plaidAccountId;

    @Column(name = "starting_balance")
    private String startingBalance;

    @JsonIgnore
    @Column(name = "plaid_access_token")
    private String plaidAccessToken;

    @Column(name = "account_number_mask")
    private String accountNumberMask;

    @Column(name = "plaid_account_name")
    private String plaidAccountName;

    @Column(name = "plaid_institution_id")
    private String plaidInstitutionId;

    @Column(name = "notes")
    private String notes;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;


    public Long getBankAccountId() {
        return bankAccountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getPlaidAccountId() {
        return plaidAccountId;
    }

    public void setPlaidAccountId(String plaidAccountId) {
        this.plaidAccountId = plaidAccountId;
    }

    public String getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(String startingBalance) {
        this.startingBalance = startingBalance;
    }

    public String getPlaidAccessToken() {
        return plaidAccessToken;
    }

    public void setPlaidAccessToken(String plaidAccessToken) {
        this.plaidAccessToken = plaidAccessToken;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPlaidInstitutionId() {
        return plaidInstitutionId;
    }

    public void setPlaidInstitutionId(String plaidInstitutionId) {
        this.plaidInstitutionId = plaidInstitutionId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountNumberMask() {
        return accountNumberMask;
    }

    public void setAccountNumberMask(String accountNumberMask) {
        this.accountNumberMask = accountNumberMask;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getPlaidAccountName() {
        return plaidAccountName;
    }

    public void setPlaidAccountName(String plaidAccountName) {
        this.plaidAccountName = plaidAccountName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
