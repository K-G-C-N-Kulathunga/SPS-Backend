package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DEPT_TYPE")
public class DeptType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DEPT_TYPE_CODE", length = 20, nullable = false)
    private String deptTypeCode;

    @Column(name = "NAME", length = 100)
    private String name;

    public DeptType() {}

    public String getDeptTypeCode() { return deptTypeCode; }


    public void setDeptTypeCode(String deptTypeCode) { this.deptTypeCode = deptTypeCode; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}