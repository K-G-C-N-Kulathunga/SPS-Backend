package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SPSTRUTM")
public class Spstrutm {

    @EmbeddedId
    private SpstrutmId id;

    @Column(name = "MAT_QTY")
    private Double matQty;

    public Spstrutm() {}

    public Spstrutm(SpstrutmId id, Double matQty) {
        this.id = id;
        this.matQty = matQty;
    }

    public SpstrutmId getId() {
        return id;
    }

    public void setId(SpstrutmId id) {
        this.id = id;
    }

    public Double getMatQty() {
        return matQty;
    }

    public void setMatQty(Double matQty) {
        this.matQty = matQty;
    }
}
