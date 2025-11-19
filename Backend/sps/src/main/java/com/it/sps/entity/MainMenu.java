package com.it.sps.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MAIN_MENU")
public class MainMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MENU_CODE", length = 20, nullable = false)
    private String menuCode;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "DISPLAY_NAME", length = 100)
    private String displayName;

    @Column(name = "ORDER_KEY")
    private Integer orderKey;

    public MainMenu() {
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}