package com.it.sps.dto;

public class MainMenuDTO {
    private String menuCode;
    private String displayName;
    private String description;
    private Integer orderKey; // Add this field

    public MainMenuDTO(String menuCode, String displayName, String description, Integer orderKey) {
        this.menuCode = menuCode;
        this.displayName = displayName;
        this.description = description;
        this.orderKey = orderKey;
    }

    // Getters and setters...
    public String getMenuCode() { return menuCode; }
    public void setMenuCode(String menuCode) { this.menuCode = menuCode; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getOrderKey() { return orderKey; }
    public void setOrderKey(Integer orderKey) { this.orderKey = orderKey; }
}