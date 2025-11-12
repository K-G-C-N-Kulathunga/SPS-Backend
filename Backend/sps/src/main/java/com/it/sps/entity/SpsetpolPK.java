package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpsetpolPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "APPLICATION_NO", length = 21, nullable = false)
    private String applicationNo;

    @Column(name = "DEPT_ID", length = 6, nullable = false)
    private String deptId;

    @Column(name = "POINT_TYPE", length = 10, nullable = false)
    private String pointType;

    @Column(name = "POLE_TYPE", length = 15, nullable = false)
    private String poleType;

    @Column(name = "FROM_CONDUCTOR", length = 10, nullable = false)
    private String fromConductor;

    @Column(name = "TO_CONDUCTOR", length = 10, nullable = false)
    private String toConductor;

    @Column(name = "MAT_CD", length = 20, nullable = false)
    private String matCd;

    public SpsetpolPK() {
    }

    public SpsetpolPK(String applicationNo, String deptId, String pointType, String poleType, String fromConductor,
            String toConductor, String matCd) {
        this.applicationNo = applicationNo;
        this.deptId = deptId;
        this.pointType = pointType;
        this.poleType = poleType;
        this.fromConductor = fromConductor;
        this.toConductor = toConductor;
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

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getPoleType() {
        return poleType;
    }

    public void setPoleType(String poleType) {
        this.poleType = poleType;
    }

    public String getFromConductor() {
        return fromConductor;
    }

    public void setFromConductor(String fromConductor) {
        this.fromConductor = fromConductor;
    }

    public String getToConductor() {
        return toConductor;
    }

    public void setToConductor(String toConductor) {
        this.toConductor = toConductor;
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
        if (!(o instanceof SpsetpolPK))
            return false;
        SpsetpolPK that = (SpsetpolPK) o;
        return Objects.equals(applicationNo, that.applicationNo) &&
                Objects.equals(deptId, that.deptId) &&
                Objects.equals(pointType, that.pointType) &&
                Objects.equals(poleType, that.poleType) &&
                Objects.equals(fromConductor, that.fromConductor) &&
                Objects.equals(toConductor, that.toConductor) &&
                Objects.equals(matCd, that.matCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationNo, deptId, pointType, poleType, fromConductor, toConductor, matCd);
    }
}
