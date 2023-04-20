package com.kent.logindemo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kent.logindemo.model.bean.UserDetail;

public interface UserDetailRepo extends JpaRepository<UserDetail, Integer> {
    
}
