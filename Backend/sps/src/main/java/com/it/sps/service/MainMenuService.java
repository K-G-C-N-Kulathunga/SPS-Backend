package com.it.sps.service;

import com.it.sps.dto.MainMenuDTO;
import java.util.List;

public interface MainMenuService {
    MainMenuDTO create(MainMenuDTO dto);
    MainMenuDTO update(String menuCode, MainMenuDTO dto);
    MainMenuDTO getById(String menuCode);
    List<MainMenuDTO> getAllOrdered();
    void delete(String menuCode);
}
