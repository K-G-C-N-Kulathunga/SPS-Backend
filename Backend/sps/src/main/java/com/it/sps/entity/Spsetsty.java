package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "SPSETSTY")
public class Spsetsty implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SpsetstyPK id;

    @Column(name = "MAT_QTY", precision = 6, scale = 2)
    private BigDecimal matQty;

    public Spsetsty() {
    }

    public SpsetstyPK getId() {
        return id;
    }

    public void setId(SpsetstyPK id) {
        this.id = id;
    }

    public BigDecimal getMatQty() {
        return matQty;
    }

    public void setMatQty(BigDecimal matQty) {
        this.matQty = matQty;
    }
}
