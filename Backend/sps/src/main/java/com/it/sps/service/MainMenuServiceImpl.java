package com.it.sps.service;

import com.it.sps.dto.MainMenuDTO;
import com.it.sps.entity.MainMenu;
import com.it.sps.repository.MainMenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainMenuServiceImpl implements MainMenuService {

    private final MainMenuRepository mainMenuRepository;

    public MainMenuServiceImpl(MainMenuRepository mainMenuRepository) {
        this.mainMenuRepository = mainMenuRepository;
    }

    @Override
    public List<MainMenuDTO> getAllMenus() {
        return mainMenuRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MainMenuDTO getMenuByCode(String menuCode) {
        MainMenu menu = mainMenuRepository.findById(menuCode)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        return convertToDTO(menu);
    }

    private MainMenuDTO convertToDTO(MainMenu menu) {
        return new MainMenuDTO(
                menu.getMenuCode(),
                menu.getDisplayName(),
                menu.getDescription(),
                menu.getOrderKey()
        );
    }
}
