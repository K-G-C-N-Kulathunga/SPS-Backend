package com.it.sps.controller;

import com.it.sps.dto.MainMenuDTO;
import com.it.sps.service.MainMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/main-menus")
public class MainMenuController {

    private final MainMenuService mainMenuService;

    public MainMenuController(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    // GET all menus
    @GetMapping
    public ResponseEntity<List<MainMenuDTO>> getAllMenus() {
        return ResponseEntity.ok(mainMenuService.getAllMenus());
    }

    // GET menu by code
    @GetMapping("/{menuCode}")
    public ResponseEntity<MainMenuDTO> getMenuByCode(@PathVariable String menuCode) {
        return ResponseEntity.ok(mainMenuService.getMenuByCode(menuCode));
    }
}
