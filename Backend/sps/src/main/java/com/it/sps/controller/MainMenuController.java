package com.it.sps.controller;

import com.it.sps.dto.MainMenuDTO;
import com.it.sps.service.MainMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main-menus")
public class MainMenuController {

    private final MainMenuService service;

    public MainMenuController(MainMenuService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MainMenuDTO> create(@RequestBody MainMenuDTO dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{menuCode}")
    public ResponseEntity<MainMenuDTO> update(
            @PathVariable String menuCode,
            @RequestBody MainMenuDTO dto
    ) {
        return ResponseEntity.ok(service.update(menuCode, dto));
    }

    @GetMapping("/{menuCode}")
    public ResponseEntity<MainMenuDTO> getById(@PathVariable String menuCode) {
        return ResponseEntity.ok(service.getById(menuCode));
    }

    @GetMapping
    public ResponseEntity<List<MainMenuDTO>> getAllOrdered() {
        return ResponseEntity.ok(service.getAllOrdered());
    }

    @DeleteMapping("/{menuCode}")
    public ResponseEntity<Void> delete(@PathVariable String menuCode) {
        service.delete(menuCode);
        return ResponseEntity.noContent().build();
    }
}
