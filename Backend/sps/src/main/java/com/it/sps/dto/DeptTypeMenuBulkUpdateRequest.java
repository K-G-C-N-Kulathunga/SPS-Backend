package com.it.sps.dto;

import java.util.List;

public class DeptTypeMenuBulkUpdateRequest {

    private List<String> menuCodes;

    public List<String> getMenuCodes() {
        return menuCodes;
    }

    public void setMenuCodes(List<String> menuCodes) {
        this.menuCodes = menuCodes;
    }
}
