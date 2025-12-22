package com.it.sps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TARIFF")
public class Tariff {

    @Id
    @Column(name = "TARIFF_CODE", length = 2, nullable = false)
    private String tariffCode;

    @Column(name = "TARIFF_NAME", length = 40, nullable = false)
    private String tariffName;

    @Column(name = "TARIFF_CAT_CODE", length = 2, nullable = false)
    private String tariffCatCode;

    @Column(name = "IS_SMC_ACTIVE", length = 1)
    private String isSmcActive;
}