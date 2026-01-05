package com.it.sps.entity;

import java.io.Serializable;
import java.util.Objects;

public class TaskUserCategoryId implements Serializable {
    private String userRoleCode;
    private String menuCode;
    private String activityCode;

    public TaskUserCategoryId() {}

    public TaskUserCategoryId(String userRoleCode, String menuCode, String activityCode) {
        this.userRoleCode = userRoleCode;
        this.menuCode = menuCode;
        this.activityCode = activityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskUserCategoryId)) return false;
        TaskUserCategoryId that = (TaskUserCategoryId) o;
        return Objects.equals(userRoleCode, that.userRoleCode)
                && Objects.equals(menuCode, that.menuCode)
                && Objects.equals(activityCode, that.activityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRoleCode, menuCode, activityCode);
    }
}
