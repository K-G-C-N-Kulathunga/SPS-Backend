package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DEPT_TYPE_MENU")
public class DeptTypeMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DeptTypeMenuId id;

    @ManyToOne
    @JoinColumn(name = "MENU_CODE", insertable = false, updatable = false)
    private MainMenu mainMenu;

    @ManyToOne
    @JoinColumn(name = "DEPT_TYPE_CODE", referencedColumnName = "DEPT_TYPE_CODE", insertable = false, updatable = false)
    private DeptType deptTypeEntity;

    public DeptTypeMenu() {}

    public DeptTypeMenuId getId() {
        return id;
    }

    public void setId(DeptTypeMenuId id) {
        this.id = id;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

}