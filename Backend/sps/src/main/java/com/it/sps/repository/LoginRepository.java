package com.it.sps.repository;

import com.it.sps.entity.Sauserm;
import com.it.sps.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LoginRepository extends JpaRepository<Sauserm, String> {

    @Query("SELECT s.rptUser FROM Sauserm s WHERE TRIM(s.userId) = :userId")
    String findRptUserByUserId(@Param("userId") String userId);

    @Query("SELECT s.userLevel FROM Sauserm s WHERE TRIM(s.userId) = :userId")
    String findUserLevelByUserId(@Param("userId") String userId);

    @Query("SELECT NEW com.it.sps.dto.LoginBranchInfoDTO(" +
            "g.deptId, g.deptType, d.name, dtm.id.menuCode, m.displayName, t.activityCode, t.activity) " +
            "FROM Sauserm s " +
            "JOIN Gldeptin g ON TRIM(UPPER(function('substr', s.rptUser, 1, 6))) = TRIM(UPPER(g.deptId)) " +
            "JOIN DeptType d ON TRIM(UPPER(d.deptTypeCode)) = TRIM(UPPER(g.deptType)) " +
            "JOIN DeptTypeMenu dtm ON TRIM(UPPER(dtm.id.deptTypeCode)) = TRIM(UPPER(d.deptTypeCode)) " +
            "JOIN MainMenu m ON TRIM(UPPER(m.menuCode)) = TRIM(UPPER(dtm.id.menuCode)) " +
            "JOIN Task t ON TRIM(UPPER(t.menuCode)) = TRIM(UPPER(m.menuCode)) " +
            "WHERE TRIM(UPPER(s.userId)) = TRIM(UPPER(:userId))")
    List<LoginBranchInfoDTO> getLoginBranchInfo(@Param("userId") String userId);

    // filter menus by logged user's RPT_USER (branch) and DEPT_TYPE
    @Query("SELECT DISTINCT NEW com.it.sps.dto.MainMenuDTO(m.menuCode, m.displayName, m.description, m.orderKey) " +
            "FROM Sauserm s " +
            "JOIN Gldeptin g ON TRIM(UPPER(function('substr', s.rptUser, 1, 6))) = TRIM(UPPER(g.deptId)) " +
            "JOIN DeptType d ON TRIM(UPPER(d.deptTypeCode)) = TRIM(UPPER(g.deptType)) " +
            "JOIN DeptTypeMenu dtm ON TRIM(UPPER(dtm.id.deptTypeCode)) = TRIM(UPPER(d.deptTypeCode)) " +
            "JOIN MainMenu m ON TRIM(UPPER(m.menuCode)) = TRIM(UPPER(dtm.id.menuCode)) " +
            "WHERE TRIM(UPPER(s.userId)) = TRIM(UPPER(:userId)) " +
            "ORDER BY m.orderKey")
    List<MainMenuDTO> getMainMenusForUser(@Param("userId") String userId);

    // fetches tasks for the given menu code and only for the logged-in user's level (userLevel)
    @Query("SELECT NEW com.it.sps.dto.TaskDTO(tuc.menuCode, t.activityCode, t.activity, t.page) " +
            "FROM Sauserm s " +
            "JOIN TaskUserCategory tuc ON TRIM(UPPER(s.userLevel)) = TRIM(UPPER(tuc.userRoleCode)) " +
            "JOIN Task t ON TRIM(UPPER(tuc.activityCode)) = TRIM(UPPER(t.activityCode)) " +
            "WHERE TRIM(UPPER(s.userId)) = TRIM(UPPER(:userId)) AND TRIM(UPPER(tuc.menuCode)) = TRIM(UPPER(:menuCode)) " +
            "ORDER BY t.orderKey")
    List<TaskDTO> getTasksForMenuAndUser(@Param("userId") String userId, @Param("menuCode") String menuCode);

    // Fallback: return all main menus regardless of user
    @Query("SELECT NEW com.it.sps.dto.MainMenuDTO(m.menuCode, m.displayName, m.description, m.orderKey) " +
            "FROM MainMenu m ORDER BY m.orderKey")
    List<MainMenuDTO> getAllMainMenus();

    // Fallback: return all tasks for a menu regardless of user role
    @Query("SELECT NEW com.it.sps.dto.TaskDTO(t.menuCode, t.activityCode, t.activity, t.page) " +
            "FROM Task t WHERE t.menuCode = :menuCode ORDER BY t.orderKey")
    List<TaskDTO> getTasksForMenu(@Param("menuCode") String menuCode);

//    @Query("SELECT DISTINCT NEW com.it.sps.dto.MainMenuDTO(dtm.mainMenu.menuCode, dtm.mainMenu.displayName, dtm.mainMenu.description) " +
//            "FROM DeptTypeMenu dtm " +
//
//            "WHERE dtm.deptType.deptType = 'AREA'")
//    List<MainMenuDTO> getMainMenusForUser(@Param("userId") String userId);

//    @Query("SELECT DISTINCT NEW com.it.sps.dto.MainMenuDTO(dtm.mainMenu.menuCode, dtm.mainMenu.displayName, dtm.mainMenu.description) " +
//            "FROM Gldeptin g " +
//
//            "WHERE g.deptType.deptType = 'AREA'")
//    List<MainMenuDTO> getMainMenusForUser2(@Param("userId") String userId);



//    @Query("SELECT DISTINCT NEW com.it.sps.dto.MainMenuDTO(dtm.mainMenu.menuCode, dtm.mainMenu.displayName, dtm.mainMenu.description) " +
//            "FROM Sauserm s ,Gldeptin g ,DeptTypeMenu dtm " +
//
//
//            "WHERE s.userId = '42360ES1' " +
//            "AND s.rptUser = g.deptId " +
//            "AND dtm.deptType.deptType = g.deptTypeEntity.deptType"
//    )
//    List<MainMenuDTO> getMainMenusForUser(@Param("userId") String userId);
}




