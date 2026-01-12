package com.it.sps.service;

import com.it.sps.dto.TaskDTO;
import java.util.List;

public interface TaskService {
    List<TaskDTO> getAllTasks();
    List<TaskDTO> getTasksByMenuCode(String menuCode);

    TaskDTO createTask(TaskDTO dto);
    TaskDTO updateTask(String activityCode, TaskDTO dto);
    void deleteTask(String activityCode);
}
