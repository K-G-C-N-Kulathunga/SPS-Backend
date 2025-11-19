package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuTaskId implements Serializable {
    @Column(name = "MENU_CODE", length = 20, nullable = false)
    private String menuCode;

    @Column(name = "ACTIVITY_CODE", length = 20, nullable = false)
    private String activityCode;

    public MenuTaskId() {}

    public MenuTaskId(String menuCode, String activityCode) {
        this.menuCode = menuCode;
        this.activityCode = activityCode;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuTaskId)) return false;
        MenuTaskId that = (MenuTaskId) o;
        return Objects.equals(menuCode, that.menuCode) &&
                Objects.equals(activityCode, that.activityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuCode, activityCode);
    }
}