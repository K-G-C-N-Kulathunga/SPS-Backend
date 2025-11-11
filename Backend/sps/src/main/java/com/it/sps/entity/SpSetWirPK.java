package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * The primary key class for the SPSETWIR database table.
 */
@Embeddable
public class SpSetWirPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "APPLICATION_NO")
    private String applicationNo;

    @Column(name = "DEPT_ID")
    private String deptId;

    @Column(name = "MAT_CD")
    private String matCd;

    public SpSetWirPK() {
    }

    public SpSetWirPK(String applicationNo, String deptId, String matCd) {
        this.applicationNo = applicationNo;
        this.deptId = deptId;
        this.matCd = matCd;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getMatCd() {
        return matCd;
    }

    public void setMatCd(String matCd) {
        this.matCd = matCd;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof SpSetWirPK)) return false;
        SpSetWirPK castOther = (SpSetWirPK) other;
        return this.applicationNo.equals(castOther.applicationNo)
                && this.deptId.equals(castOther.deptId)
                && this.matCd.equals(castOther.matCd);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.applicationNo.hashCode();
        hash = hash * prime + this.deptId.hashCode();
        hash = hash * prime + this.matCd.hashCode();
        return hash;
    }
}
