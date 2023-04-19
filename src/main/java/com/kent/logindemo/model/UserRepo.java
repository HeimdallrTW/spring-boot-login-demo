package com.kent.logindemo.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

    // find user by email
    public User findByEmail(String email);
    
}
