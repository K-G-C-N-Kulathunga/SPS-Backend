package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TASK_USER_CATEGORY")
public class TaskUserCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ROLE_CODE", length = 20, nullable = false)
    private String userRoleCode;

    @Id
    @Column(name = "MENU_CODE", length = 20, nullable = false)
    private String menuCode;

    @Id
    @Column(name = "ACTIVITY_CODE", length = 20, nullable = false)
    private String activityCode;


    @ManyToOne
    @JoinColumn(name = "USER_ROLE_CODE", referencedColumnName = "USER_ID", insertable = false, updatable = false)
    private UserCategory userCategory;

    @ManyToOne
    @JoinColumn(name = "MENU_CODE", referencedColumnName = "MENU_CODE", insertable = false, updatable = false)
    private MainMenu mainMenu;

    @ManyToOne
    @JoinColumn(name = "ACTIVITY_CODE", referencedColumnName = "ACTIVITY_CODE", insertable = false, updatable = false)
    private Task task;

    public TaskUserCategory() {}

    public String getUserRoleCode() {
        return userRoleCode;
    }

    public void setUserRoleCode(String userRoleCode) {
        this.userRoleCode = userRoleCode;
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

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public Task getTask() { return task; }

    public void setTask(Task task) { this.task = task; }
}