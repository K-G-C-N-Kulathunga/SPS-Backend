

package com.it.sps.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Objects;

/**
 * Primary key class for SPSEREST table.
 */
@Embeddable
public class SpsErestPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "APPLICATION_NO", nullable = false, length = 20)
    private String applicationNo;

    @Column(name = "DEPT_ID", nullable = false, length = 6)
    private String deptId;

    public SpsErestPK() {
    }

    public SpsErestPK(String applicationNo, String deptId) {
        this.applicationNo = applicationNo;
        this.deptId = deptId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpsErestPK)) return false;
        SpsErestPK that = (SpsErestPK) o;
        return Objects.equals(applicationNo, that.applicationNo) &&
                Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationNo, deptId);
    }
}
