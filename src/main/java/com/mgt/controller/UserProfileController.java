package com.mgt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mgt.model.UserProfile;
import com.mgt.serviceimpl.UserProfileService;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200") // adjust to your frontend URL
public class UserProfileController {

    @Autowired
    private UserProfileService service;


}