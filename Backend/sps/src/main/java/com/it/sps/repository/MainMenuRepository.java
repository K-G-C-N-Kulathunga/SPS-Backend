package com.it.sps.repository;

import com.it.sps.entity.MainMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainMenuRepository extends JpaRepository<MainMenu, String> {
    List<MainMenu> findAllByOrderByOrderKeyAsc();
}
