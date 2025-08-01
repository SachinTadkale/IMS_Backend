package com.mgt.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mgt.jwtServices.JwtService;
import com.mgt.model.User;
import com.mgt.repository.UserRepo;

@Service
public class AuthUserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;

    /**
     * Fetch the authenticated user from Authorization header.
     *
     * @param authorizationHeader The "Authorization" header value
     * @return Authenticated User
     */
    public User getAuthenticatedUser(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        Long userId = jwtService.extractUserId(token);

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }

        return userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    /**
     * Extract only UserId if needed.
     */
    public Long getAuthenticatedUserId(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        Long userId = jwtService.extractUserId(token);

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }

        return userId;
    }

}
