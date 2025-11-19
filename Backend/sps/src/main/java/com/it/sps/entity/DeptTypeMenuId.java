package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DeptTypeMenuId implements Serializable {
    @Column(name = "DEPT_TYPE_CODE", length = 20)
    private String deptTypeCode;

    @Column(name = "MENU_CODE", length = 20)
    private String menuCode;

    public DeptTypeMenuId() {}

    public DeptTypeMenuId(String deptTypeCode, String menuCode) {
        this.deptTypeCode = deptTypeCode;
        this.menuCode = menuCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeptTypeMenuId)) return false;
        DeptTypeMenuId that = (DeptTypeMenuId) o;
        return Objects.equals(deptTypeCode, that.deptTypeCode) &&
                Objects.equals(menuCode, that.menuCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptTypeCode, menuCode);
    }

    public String getDeptTypeCode() { return deptTypeCode; }

    public void setDeptTypeCode(String deptTypeCode) { this.deptTypeCode = deptTypeCode; }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

}