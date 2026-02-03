package com.it.sps.dto;

import java.util.List;

public class SaveAssignmentsRequest {
    public String userId;
    public List<Item> items;

    public static class Item {
        public String menuCode;
        public String activityCode;
    }
}
