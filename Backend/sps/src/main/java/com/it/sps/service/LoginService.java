package com.it.sps.service;

import com.it.sps.dto.LoginBranchInfoDTO;
import com.it.sps.dto.*;
import com.it.sps.repository.LoginRepository;
import com.it.sps.repository.GldeptinRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LoginService {

    private final LoginRepository loginRepository;
    private final GldeptinRepository gldeptinRepository;

    public LoginService(LoginRepository loginRepository, GldeptinRepository gldeptinRepository) {
        this.loginRepository = loginRepository;
        this.gldeptinRepository = gldeptinRepository;
    }

    public LoginResponseDTO getLoginInfo(String userId) {
        String trimmedUserId = userId.trim();
        String userLevel = null;
        List<LoginBranchInfoDTO> branchInfoList = null;
        String deptId = null;
        String message = null;

        try {
            // Step 1: Fetch userLevel
            userLevel = loginRepository.findUserLevelByUserId(trimmedUserId);
            System.out.println("Fetched userLevel: " + userLevel);

            if (userLevel == null) {
                message = "User level not set for userId: " + trimmedUserId;
            }

            // Step 2: Fetch rptUser
            String rptUser = loginRepository.findRptUserByUserId(trimmedUserId);
            System.out.println("Fetched rptUser: " + rptUser);

            if (rptUser != null) {
                // Step 3: Fetch deptId using rptUser
                deptId = gldeptinRepository.findDeptIdByRptUser(rptUser);
                System.out.println("Fetched deptId: " + deptId);
            }

            // Step 4: Fetch branch/menu/activity info
            branchInfoList = loginRepository.getLoginBranchInfo(trimmedUserId);

            if (branchInfoList.isEmpty()) {
                message = "No branch data found for DEPT_ID: " + deptId;
            }

        } catch (Exception e) {
            System.out.println("Exception in getLoginInfo: " + e.getMessage());
            e.printStackTrace();
            message = "Error fetching login info: " + e.getMessage();
        }

        return new LoginResponseDTO(userLevel, branchInfoList, deptId, message);
    }

    public List<MainMenuDTO> getMainMenusForUser(String userId) {
        return loginRepository.getMainMenusForUser(userId.trim());
    }

    public List<TaskDTO> getTasksForMenuAndUser(String userId, String menuCode) {
        return loginRepository.getTasksForMenuAndUser(userId.trim(), menuCode.trim());
    }
}

//// Backend/sps/src/main/java/com/it/sps/service/LoginService.java
//package com.it.sps.service;
//
//import com.it.sps.dto.LoginBranchInfoDTO;
//import com.it.sps.dto.LoginResponseDTO;
//import com.it.sps.repository.LoginRepository;
//import com.it.sps.repository.GldeptinRepository;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class LoginService {
//    private final LoginRepository loginRepository;
//    private final GldeptinRepository gldeptinRepository; // Add this line
//
//    public LoginService(LoginRepository loginRepository, GldeptinRepository gldeptinRepository) { // Update constructor
//        this.loginRepository = loginRepository;
//        this.gldeptinRepository = gldeptinRepository;
//    }
//
//    public LoginResponseDTO getLoginInfo(String userId) {
//        String trimmedUserId = userId.trim();
//        System.out.println("Input userId: " + trimmedUserId);
//
//        String userLevel = null;
//        System.out.println("Fetched userLevel: " + userLevel);
//
//        List<LoginBranchInfoDTO> branchInfoList = null;
//        String deptId = null;
//        System.out.println("Fetched deptId: " + deptId);
//        String message = null;
//
//        try {
//            userLevel = loginRepository.findUserLevelByUserId(trimmedUserId);
//            System.out.println("Fetched userLevel for userId " + trimmedUserId + ": " + userLevel);
//
//            branchInfoList = loginRepository.getLoginBranchInfo(trimmedUserId);
//            deptId = gldeptinRepository.findDeptIdByRptUser(trimmedUserId);
//
//            if (branchInfoList.isEmpty()) {
//                message = "No branch data found for DEPT_ID: " + deptId;
//            }
//        } catch (Exception e) {
//            System.out.println("Exception in getLoginInfo: " + e.getMessage());
//            e.printStackTrace();
//            message = "Error fetching login info: " + e.getMessage();
//        }
//
//        return new LoginResponseDTO(userLevel, branchInfoList, deptId, message);
//    }
//}