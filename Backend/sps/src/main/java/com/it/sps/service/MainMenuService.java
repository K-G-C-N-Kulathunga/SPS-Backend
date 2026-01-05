package com.it.sps.service;

import com.it.sps.dto.MainMenuDTO;
import java.util.List;

public interface MainMenuService {

    List<MainMenuDTO> getAllMenus();

    MainMenuDTO getMenuByCode(String menuCode);
}
