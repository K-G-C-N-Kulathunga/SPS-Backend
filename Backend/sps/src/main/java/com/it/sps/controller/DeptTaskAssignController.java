package com.it.sps.controller;

import com.it.sps.dto.*;
import com.it.sps.entity.DeptTypeMenu;
import com.it.sps.entity.MainMenu;
import com.it.sps.entity.Task;
import com.it.sps.entity.TaskUserCategory;
import com.it.sps.repository.DeptTypeMenuRepository;
import com.it.sps.repository.DeptTypeRepository;
import com.it.sps.repository.TaskRepository;
import com.it.sps.repository.TaskUserCategoryRepository;
import com.it.sps.repository.UserCategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dept-task-assign")
public class DeptTaskAssignController {

    private final DeptTypeRepository deptRepo;
    private final DeptTypeMenuRepository deptMenuRepo;
    private final TaskRepository taskRepo;
    private final TaskUserCategoryRepository tucRepo;
    private final UserCategoryRepository userRepo;

    public DeptTaskAssignController(
            DeptTypeRepository deptRepo,
            DeptTypeMenuRepository deptMenuRepo,
            TaskRepository taskRepo,
            TaskUserCategoryRepository tucRepo,
            UserCategoryRepository userRepo
    ) {
        this.deptRepo = deptRepo;
        this.deptMenuRepo = deptMenuRepo;
        this.taskRepo = taskRepo;
        this.tucRepo = tucRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/dept/{deptTypeCode}/user/{userId}")
    public List<DeptTaskTreeDto> getTree(@PathVariable String deptTypeCode, @PathVariable String userId) {
        if (!deptRepo.existsById(deptTypeCode)) throw new RuntimeException("Dept not found: " + deptTypeCode);
        if (!userRepo.existsById(userId)) throw new RuntimeException("User not found: " + userId);

        List<DeptTypeMenu> deptMenus = deptMenuRepo.findById_DeptTypeCode(deptTypeCode);

        // menuCodes from mapping
        List<String> menuCodes = deptMenus.stream()
                .map(x -> x.getId().getMenuCode())
                .filter(Objects::nonNull)
                .toList();

        // assigned tasks for user (we filter by menuCodes)
        Set<String> assigned = new HashSet<>();
        for (TaskUserCategory r : tucRepo.findByUserRoleCode(userId)) {
            if (menuCodes.contains(r.getMenuCode())) {
                assigned.add(r.getMenuCode() + "::" + r.getActivityCode());
            }
        }

        // build tree
        List<DeptTaskTreeDto> out = new ArrayList<>();

        for (DeptTypeMenu dm : deptMenus) {
            String menuCode = dm.getId().getMenuCode();
            MainMenu mm = dm.getMainMenu();

            DeptTaskTreeDto m = new DeptTaskTreeDto();
            m.setMenuCode(menuCode);
            m.setDisplayName(mm != null ? mm.getDisplayName() : menuCode);
            m.setOrderKey(mm != null ? mm.getOrderKey() : null);

            List<Task> tasks = taskRepo.findByMenuCodeOrderByOrderKeyAscActivityAsc(menuCode);
            List<DeptTaskTreeTaskDto> tlist = new ArrayList<>();

            for (Task t : tasks) {
                DeptTaskTreeTaskDto td = new DeptTaskTreeTaskDto();
                td.setActivityCode(t.getActivityCode());
                td.setActivityName(t.getActivity()); // your TASK column is ACTIVITY
                td.setPage(t.getPage());
                td.setOrderKey(t.getOrderKey());
                td.setAssigned(assigned.contains(menuCode + "::" + t.getActivityCode()));
                tlist.add(td);
            }

            m.setTasks(tlist);
            out.add(m);
        }

        // sort menus by orderKey then displayName
        out.sort((a, b) -> {
            int oa = (a.getOrderKey() != null) ? a.getOrderKey() : Integer.MAX_VALUE;
            int ob = (b.getOrderKey() != null) ? b.getOrderKey() : Integer.MAX_VALUE;
            if (oa != ob) return Integer.compare(oa, ob);
            return String.valueOf(a.getDisplayName()).compareToIgnoreCase(String.valueOf(b.getDisplayName()));
        });

        return out;
    }

    @PutMapping("/user/{userId}")
    @Transactional
    public void save(@PathVariable String userId, @RequestBody DeptTaskAssignSaveRequest req) {
        if (!userRepo.existsById(userId)) throw new RuntimeException("User not found: " + userId);
        if (req.getDeptTypeCode() == null || req.getDeptTypeCode().trim().isEmpty())
            throw new RuntimeException("deptTypeCode required");

        String deptTypeCode = req.getDeptTypeCode().trim();
        if (!deptRepo.existsById(deptTypeCode)) throw new RuntimeException("Dept not found: " + deptTypeCode);

        // Only allow saving tasks for menus that belong to this dept
        List<String> deptMenuCodes = deptMenuRepo.findById_DeptTypeCode(deptTypeCode).stream()
                .map(x -> x.getId().getMenuCode())
                .toList();

        // delete existing assignments in those menus
        tucRepo.deleteByUserRoleCodeAndMenuCodeIn(userId, deptMenuCodes);

        // insert new
        List<DeptTaskAssignItemDto> items = (req.getItems() == null) ? List.of() : req.getItems();

        for (DeptTaskAssignItemDto it : items) {
            if (it.getMenuCode() == null || it.getActivityCode() == null) continue;

            String menuCode = it.getMenuCode().trim();
            String activityCode = it.getActivityCode().trim();
            if (menuCode.isEmpty() || activityCode.isEmpty()) continue;

            if (!deptMenuCodes.contains(menuCode)) continue; // ignore invalid menu

            TaskUserCategory row = new TaskUserCategory();
            row.setUserRoleCode(userId);
            row.setMenuCode(menuCode);
            row.setActivityCode(activityCode);
            tucRepo.save(row);
        }
    }
}
