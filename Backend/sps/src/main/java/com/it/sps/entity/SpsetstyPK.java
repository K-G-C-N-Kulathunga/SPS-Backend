package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpsetstyPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "APPLICATION_NO", length = 21, nullable = false)
    private String applicationNo;

    @Column(name = "DEPT_ID", length = 6, nullable = false)
    private String deptId;

    @Column(name = "MAT_CD", length = 20, nullable = false)
    private String matCd;

    @Column(name = "STAY_TYPE", length = 10, nullable = false)
    private String stayType;

    public SpsetstyPK() {
    }

    public SpsetstyPK(String applicationNo, String deptId, String matCd, String stayType) {
        this.applicationNo = applicationNo;
        this.deptId = deptId;
        this.matCd = matCd;
        this.stayType = stayType;
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

    public String getStayType() {
        return stayType;
    }

    public void setStayType(String stayType) {
        this.stayType = stayType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof SpsetstyPK))
            return false;
        SpsetstyPK that = (SpsetstyPK) o;
        return Objects.equals(applicationNo, that.applicationNo) &&
                Objects.equals(deptId, that.deptId) &&
                Objects.equals(matCd, that.matCd) &&
                Objects.equals(stayType, that.stayType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationNo, deptId, matCd, stayType);
    }
}
