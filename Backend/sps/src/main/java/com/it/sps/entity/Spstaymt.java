package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SPSTAYMT")
public class Spstaymt {

    @EmbeddedId
    private SpstaymtId id;

    @Column(name = "MAT_QTY")
    private Double matQty;

    public Spstaymt() {}

    public Spstaymt(SpstaymtId id, Double matQty) {
        this.id = id;
        this.matQty = matQty;
    }

    public SpstaymtId getId() {
        return id;
    }

    public void setId(SpstaymtId id) {
        this.id = id;
    }

    public Double getMatQty() {
        return matQty;
    }

    public void setMatQty(Double matQty) {
        this.matQty = matQty;
    }
}
