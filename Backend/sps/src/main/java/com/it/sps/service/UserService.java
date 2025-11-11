package com.it.sps.service;

import com.it.sps.dto.LoginRequest;
import com.it.sps.dto.LoginResponse;
import com.it.sps.entity.Sauserm;
import com.it.sps.repository.SausermRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    
    
    private final SausermRepository sausermRepository;

    // default password
    private static final String DEFAULT_PASSWORD = "1234";

    public UserService(SausermRepository sausermRepository) {
        this.sausermRepository = sausermRepository;
    }

    public LoginResponse authenticateUser (LoginRequest loginRequest) {
        String userId = loginRequest.getUserId();
        
        Sauserm user = sausermRepository.findByUserIdIgnoreCase(userId);
        
        if (user == null) {
            return new LoginResponse(false, null, null, "User  not found");
        }
        
        // Use hard-coded default password
        if (!DEFAULT_PASSWORD.equals(loginRequest.getPassword())) {
            return new LoginResponse(false, null, null, "Invalid password");
        }
        
        return new LoginResponse(
            true,
            user.getRptUser (),
            user.getUserLevel(),
            "Login successful"
        );
    }
}