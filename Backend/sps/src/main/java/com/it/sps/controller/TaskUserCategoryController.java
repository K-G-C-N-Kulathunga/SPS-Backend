package com.it.sps.controller;

import com.it.sps.dto.TaskUserCategoryDTO;
import com.it.sps.service.TaskUserCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/task-user-categories")
public class TaskUserCategoryController {

    private final TaskUserCategoryService service;

    public TaskUserCategoryController(TaskUserCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskUserCategoryDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/user/{userRoleCode}")
    public ResponseEntity<List<TaskUserCategoryDTO>> getByUser(@PathVariable String userRoleCode) {
        return ResponseEntity.ok(service.getByUserRoleCode(userRoleCode));
    }

    @GetMapping("/user/{userRoleCode}/menu/{menuCode}")
    public ResponseEntity<List<TaskUserCategoryDTO>> getByUserAndMenu(
            @PathVariable String userRoleCode,
            @PathVariable String menuCode
    ) {
        return ResponseEntity.ok(service.getByUserRoleAndMenu(userRoleCode, menuCode));
    }

    @PostMapping
    public ResponseEntity<TaskUserCategoryDTO> create(@RequestBody TaskUserCategoryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam String userRoleCode,
            @RequestParam String menuCode,
            @RequestParam String activityCode
    ) {
        service.delete(userRoleCode, menuCode, activityCode);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> notFound(NoSuchElementException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
