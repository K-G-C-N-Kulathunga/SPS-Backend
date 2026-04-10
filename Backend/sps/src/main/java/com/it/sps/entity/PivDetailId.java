package com.it.sps.entity;

import java.io.Serializable;
import java.util.Objects;

public class PivDetailId implements Serializable {

    private String pivNo;
    private String deptId;

    public PivDetailId() {}

    public PivDetailId(String pivNo, String deptId) {
        this.pivNo = pivNo;
        this.deptId = deptId;
    }

    public String getPivNo() { return pivNo; }
    public void setPivNo(String pivNo) { this.pivNo = pivNo; }

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PivDetailId)) return false;
        PivDetailId that = (PivDetailId) o;
        return Objects.equals(pivNo, that.pivNo) && Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pivNo, deptId);
    }
}
