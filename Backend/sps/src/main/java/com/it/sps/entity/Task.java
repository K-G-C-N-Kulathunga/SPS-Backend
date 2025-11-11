package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TASK")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ACTIVITY_CODE", length = 20, nullable = false)
    private String activityCode;

    @Column(name = "MENU_CODE", length = 20)
    private String menuCode;

    @Column(name = "ACTIVITY", length = 100)
    private String activity;

    @Column(name = "PAGE", length = 100)
    private String page;

    @ManyToOne
    @JoinColumn(name = "MENU_CODE", referencedColumnName = "MENU_CODE", insertable = false, updatable = false)
    private MainMenu mainMenu;

    @Column(name = "ORDER_KEY")
    private Integer orderKey;

    public Task() {}

    public String getActivityCode() { return activityCode; }
    public void setActivityCode(String activityCode) { this.activityCode = activityCode; }
    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }
    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }
    public String getPage() { return page; }
    public void setPage(String page) { this.page = page; }
    public MainMenu getMainMenu() { return mainMenu; }
    public void setMainMenu(MainMenu mainMenu) { this.mainMenu = mainMenu; }
}