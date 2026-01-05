package com.it.sps.service.impl;

import com.it.sps.dto.TaskDTO;
import com.it.sps.entity.Task;
import com.it.sps.repository.TaskRepository;
import com.it.sps.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAllAsDto();
    }

    @Override
    public List<TaskDTO> getTasksByMenuCode(String menuCode) {
        return taskRepository.findByMenuCodeAsDto(menuCode);
    }

    @Override
    @Transactional
    public TaskDTO createTask(TaskDTO dto) {
        if (dto.getActivityCode() == null || dto.getActivityCode().trim().isEmpty()) {
            throw new IllegalArgumentException("activityCode is required");
        }
        if (dto.getMenuCode() == null || dto.getMenuCode().trim().isEmpty()) {
            throw new IllegalArgumentException("menuCode is required");
        }
        if (dto.getActivityName() == null || dto.getActivityName().trim().isEmpty()) {
            throw new IllegalArgumentException("activityName is required");
        }

        String code = dto.getActivityCode().trim();
        if (taskRepository.existsById(code)) {
            throw new IllegalArgumentException("Task with activityCode already exists: " + code);
        }

        Task task = new Task();
        task.setActivityCode(code);
        task.setMenuCode(dto.getMenuCode().trim());
        task.setActivity(dto.getActivityName().trim());
        task.setPage(dto.getPage());
        task.setOrderKey(dto.getOrderKey());

        Task saved = taskRepository.save(task);
        return toDto(saved);
    }

    @Override
    @Transactional
    public TaskDTO updateTask(String activityCode, TaskDTO dto) {
        Task task = taskRepository.findById(activityCode)
                .orElseThrow(() -> new NoSuchElementException("Task not found: " + activityCode));

        // You generally should NOT change primary key (activityCode) in update.
        // So we only update other fields:
        if (dto.getMenuCode() != null) task.setMenuCode(dto.getMenuCode().trim());
        if (dto.getActivityName() != null) task.setActivity(dto.getActivityName().trim());
        if (dto.getPage() != null) task.setPage(dto.getPage());
        task.setOrderKey(dto.getOrderKey());

        Task saved = taskRepository.save(task);
        return toDto(saved);
    }

    @Override
    @Transactional
    public void deleteTask(String activityCode) {
        if (!taskRepository.existsById(activityCode)) {
            throw new NoSuchElementException("Task not found: " + activityCode);
        }
        taskRepository.deleteById(activityCode);
    }

    private TaskDTO toDto(Task t) {
        return new TaskDTO(
                t.getMenuCode(),
                t.getActivityCode(),
                t.getActivity(),
                t.getPage(),
                t.getOrderKey()
        );
    }
}
