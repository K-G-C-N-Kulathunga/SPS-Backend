package com.it.sps.repository;

import com.it.sps.dto.TaskDTO;
import com.it.sps.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByMenuCode(String menuCode);

    @Query("""
        SELECT new com.it.sps.dto.TaskDTO(
            t.menuCode,
            t.activityCode,
            t.activity,
            t.page,
            t.orderKey
        )
        FROM Task t
        ORDER BY t.orderKey ASC
    """)
    List<TaskDTO> findAllAsDto();

    @Query("""
        SELECT new com.it.sps.dto.TaskDTO(
            t.menuCode,
            t.activityCode,
            t.activity,
            t.page,
            t.orderKey
        )
        FROM Task t
        WHERE t.menuCode = :menuCode
        ORDER BY t.orderKey ASC
    """)
    List<TaskDTO> findByMenuCodeAsDto(@Param("menuCode") String menuCode);

    List<Task> findByMenuCodeOrderByOrderKeyAscActivityAsc(String menuCode);

    boolean existsByActivityCode(String activityCode);
}
