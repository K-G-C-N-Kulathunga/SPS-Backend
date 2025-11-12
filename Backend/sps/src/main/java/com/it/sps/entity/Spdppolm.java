package com.it.sps.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "SPDPPOLM")
public class Spdppolm {

    @EmbeddedId
    private SpdppolmId id;

    @Column(name = "IS_ACTIVE")
    private String isActive;

    public Spdppolm() {}

    public Spdppolm(SpdppolmId id, String isActive) {
        this.id = id;
        this.isActive = isActive;
    }

    public SpdppolmId getId() {
        return id;
    }

    public void setId(SpdppolmId id) {
        this.id = id;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
