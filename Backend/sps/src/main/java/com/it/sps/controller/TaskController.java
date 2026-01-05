package com.it.sps.controller;

import com.it.sps.dto.TaskDTO;
import com.it.sps.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ✅ Retrieve all
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // ✅ Retrieve by menuCode
    @GetMapping("/menu/{menuCode}")
    public ResponseEntity<List<TaskDTO>> getTasksByMenu(@PathVariable String menuCode) {
        return ResponseEntity.ok(taskService.getTasksByMenuCode(menuCode));
    }

    // ✅ Create
    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto) {
        TaskDTO created = taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ✅ Update (by activityCode)
    @PutMapping("/{activityCode}")
    public ResponseEntity<TaskDTO> update(
            @PathVariable String activityCode,
            @RequestBody TaskDTO dto
    ) {
        return ResponseEntity.ok(taskService.updateTask(activityCode, dto));
    }

    // ✅ Delete
    @DeleteMapping("/{activityCode}")
    public ResponseEntity<Void> delete(@PathVariable String activityCode) {
        taskService.deleteTask(activityCode);
        return ResponseEntity.noContent().build();
    }

    // Optional: basic exception handling
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> notFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
