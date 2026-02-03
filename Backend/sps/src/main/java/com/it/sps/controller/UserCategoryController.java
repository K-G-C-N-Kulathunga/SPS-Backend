package com.it.sps.controller;

import com.it.sps.dto.UserCreateRequest;
import com.it.sps.dto.UserDto;
import com.it.sps.entity.UserCategory;
import com.it.sps.repository.UserCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // this matches the frontend calls I gave you
public class UserCategoryController {

    private final UserCategoryRepository repo;

    public UserCategoryController(UserCategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<UserDto> list() {
        return repo.findAll().stream().map(u -> {
            UserDto dto = new UserDto();
            dto.userId = u.getUserId();
            dto.name = u.getName();
            return dto;
        }).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserCreateRequest req) {
        if (req.userId == null || req.userId.trim().isEmpty())
            throw new RuntimeException("userId required");
        if (req.name == null || req.name.trim().isEmpty())
            throw new RuntimeException("name required");

        String id = req.userId.trim();
        if (repo.existsById(id)) throw new RuntimeException("User already exists: " + id);

        UserCategory u = new UserCategory();
        u.setUserId(id);
        u.setName(req.name.trim());

        UserCategory saved = repo.save(u);

        UserDto dto = new UserDto();
        dto.userId = saved.getUserId();
        dto.name = saved.getName();
        return dto;
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String userId) {
        repo.deleteById(userId);
    }
}
