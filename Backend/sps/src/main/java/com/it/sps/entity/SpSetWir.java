package com.it.sps.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.*;

/**
 * The persistent class for the SPSETWIR database table.
 */
@Entity
@Table(name = "SPSETWIR")
@NamedQuery(name = "SpSetWir.findAll", query = "SELECT s FROM SpSetWir s")
public class SpSetWir implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SpSetWirPK id;

    @Column(name = "WIRE_MODE")
    private String wireMode;

    @Column(name = "WIRE_TYPE")
    private String wireType;

    @Column(name = "WIRE_LENGTH")
    private BigDecimal wireLength;

    public SpSetWir() {
    }

    public SpSetWirPK getId() {
        return id;
    }

    public void setId(SpSetWirPK id) {
        this.id = id;
    }

    public String getWireMode() {
        return wireMode;
    }

    public void setWireMode(String wireMode) {
        this.wireMode = wireMode;
    }

    public String getWireType() {
        return wireType;
    }

    public void setWireType(String wireType) {
        this.wireType = wireType;
    }

    public BigDecimal getWireLength() {
        return wireLength;
    }

    public void setWireLength(BigDecimal wireLength) {
        this.wireLength = wireLength;
    }
}
