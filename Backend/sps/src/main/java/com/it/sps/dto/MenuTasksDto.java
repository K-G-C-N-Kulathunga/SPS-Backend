package com.it.sps.dto;

import java.util.List;

public class MenuTasksDto {
    public String menuCode;
    public String displayName;
    public String description;
    public Integer orderKey;
    public List<TaskDto> tasks;

    public static class TaskDto {
        public String activityCode;
        public String activityName;
        public String page;
        public boolean assigned;
    }
}
