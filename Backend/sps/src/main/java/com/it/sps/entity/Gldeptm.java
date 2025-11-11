package com.it.sps.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "GLDEPTM")
public class Gldeptm {
    @Id
    @Column(name = "DEPT_ID", nullable = false, length = 6)
    private String deptId;

    @Column(name = "COMP_ID", length = 6)
    private String compId;

    @Column(name = "DEPT_NM", length = 60)
    private String deptNm;

    @Column(name = "CS_PS", length = 5)
    private String csPs;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "LOG_ID")
    private Integer logId;

    @Column(name = "ENT_BY", length = 12)
    private String entBy;

    @Column(name = "ENT_DT")
    private LocalDate entDt;

    @Column(name = "MODI_BY", length = 12)
    private String modiBy;

    @Column(name = "MODI_DT")
    private LocalDate modiDt;

    @Lob
    @Column(name = "FILTER")
    private String filter;

}