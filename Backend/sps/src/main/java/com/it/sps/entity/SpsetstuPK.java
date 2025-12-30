package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpsetstuPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "APPLICATION_NO", length = 21, nullable = false)
    private String applicationNo;

    @Column(name = "DEPT_ID", length = 6, nullable = false)
    private String deptId;

    @Column(name = "MAT_CD", length = 20, nullable = false)
    private String matCd;

    public SpsetstuPK() {
    }

    public SpsetstuPK(String applicationNo, String deptId, String matCd) {
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SpsetstuPK))
            return false;
        SpsetstuPK that = (SpsetstuPK) o;
        return Objects.equals(applicationNo, that.applicationNo) &&
                Objects.equals(deptId, that.deptId) &&
                Objects.equals(matCd, that.matCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationNo, deptId, matCd);
    }
}
