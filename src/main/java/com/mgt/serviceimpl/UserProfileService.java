package com.mgt.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgt.model.UserProfile;
import com.mgt.repository.UserProfileRepository;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository repo;

}