package com.it.sps.entity;

import java.io.Serializable;
import java.util.Objects;

public class PivAmountId implements Serializable {

    private String deptId;
    private String pivNo;
    private String accountCode;

    public PivAmountId() {}

    public PivAmountId(String deptId, String pivNo, String accountCode) {
        this.deptId = deptId;
        this.pivNo = pivNo;
        this.accountCode = accountCode;
    }

    public String getDeptId() { return deptId; }
    public void setDeptId(String deptId) { this.deptId = deptId; }

    public String getPivNo() { return pivNo; }
    public void setPivNo(String pivNo) { this.pivNo = pivNo; }

    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PivAmountId)) return false;
        PivAmountId that = (PivAmountId) o;
        return Objects.equals(deptId, that.deptId)
                && Objects.equals(pivNo, that.pivNo)
                && Objects.equals(accountCode, that.accountCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, pivNo, accountCode);
    }
}
