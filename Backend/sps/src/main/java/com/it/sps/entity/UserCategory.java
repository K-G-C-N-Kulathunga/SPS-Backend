package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_CATEGORY")
public class UserCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Column(name = "NAME", length = 100)
    private String name;

    public UserCategory() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}