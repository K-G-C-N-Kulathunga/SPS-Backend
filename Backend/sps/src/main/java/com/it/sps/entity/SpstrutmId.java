package com.it.sps.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SpstrutmId implements Serializable {

    private String deptId;
    private String matCd;

    public SpstrutmId() {}

    public SpstrutmId(String deptId, String matCd) {
        this.deptId = deptId;
        this.matCd = matCd;
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
        if (this == o) return true;
        if (!(o instanceof SpstrutmId)) return false;
        SpstrutmId that = (SpstrutmId) o;
        return Objects.equals(deptId, that.deptId) && Objects.equals(matCd, that.matCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptId, matCd);
    }
}
