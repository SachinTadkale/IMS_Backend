package com.mgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgt.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

  

}
