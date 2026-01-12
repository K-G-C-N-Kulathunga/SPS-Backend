package com.it.sps.service.impl;

import com.it.sps.dto.MainMenuDTO;
import com.it.sps.entity.MainMenu;
import com.it.sps.repository.MainMenuRepository;
import com.it.sps.service.MainMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MainMenuServiceImpl implements MainMenuService {

    private final MainMenuRepository repo;

    public MainMenuServiceImpl(MainMenuRepository repo) {
        this.repo = repo;
    }

    @Override
    public MainMenuDTO create(MainMenuDTO dto) {
        if (dto.getMenuCode() == null || dto.getMenuCode().isBlank()) {
            throw new IllegalArgumentException("menuCode is required");
        }
        if (repo.existsById(dto.getMenuCode())) {
            throw new IllegalArgumentException("menuCode already exists: " + dto.getMenuCode());
        }

        MainMenu entity = toEntity(dto);
        MainMenu saved = repo.save(entity);
        return toDto(saved);
    }

    @Override
    public MainMenuDTO update(String menuCode, MainMenuDTO dto) {
        MainMenu existing = repo.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("MainMenu not found: " + menuCode));

        // keep id stable
        existing.setDescription(dto.getDescription());
        existing.setDisplayName(dto.getDisplayName());
        existing.setOrderKey(dto.getOrderKey());

        MainMenu saved = repo.save(existing);
        return toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MainMenuDTO getById(String menuCode) {
        MainMenu entity = repo.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("MainMenu not found: " + menuCode));
        return toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MainMenuDTO> getAllOrdered() {
        return repo.findAllByOrderByOrderKeyAsc()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void delete(String menuCode) {
        if (!repo.existsById(menuCode)) {
            throw new IllegalArgumentException("MainMenu not found: " + menuCode);
        }
        repo.deleteById(menuCode);
    }

    private MainMenuDTO toDto(MainMenu e) {
        return new MainMenuDTO(
                e.getMenuCode(),
                e.getDescription(),
                e.getDisplayName(),
                e.getOrderKey()
        );
    }

    private MainMenu toEntity(MainMenuDTO d) {
        MainMenu e = new MainMenu();
        e.setMenuCode(d.getMenuCode());
        e.setDescription(d.getDescription());
        e.setDisplayName(d.getDisplayName());
        e.setOrderKey(d.getOrderKey());
        return e;
    }
}
