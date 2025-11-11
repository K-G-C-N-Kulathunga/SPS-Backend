package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "APPLICATION_OTHER_ACCOUNTS")
@IdClass(ApplicationOtherAccountId.class)
public class ApplicationOtherAccount {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Id
    @Column(name = "ACCOUNT_NO", nullable = false)
    private String accountNo;

    // Constructors
    public ApplicationOtherAccount() {}

    public ApplicationOtherAccount(String id, String accountNo) {
        this.id = id;
        this.accountNo = accountNo;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
