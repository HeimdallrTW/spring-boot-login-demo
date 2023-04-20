package com.kent.logindemo.service;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kent.logindemo.model.bean.LoginRequest;
import com.kent.logindemo.model.bean.User;
import com.kent.logindemo.model.bean.UserDetail;
import com.kent.logindemo.model.repository.UserDetailRepo;
import com.kent.logindemo.model.repository.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;

    public UserService(UserRepo userRepo, UserDetailRepo userDetailRepo) {
        this.userRepo = userRepo;
        this.userDetailRepo = userDetailRepo;
    }

    /**
     * Find user by email
     * 
     * @param email user email
     * @return user
     */
    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userRepo.findByEmail(email));
    }

    /**
     * Find user by id
     * 
     * @param id user id
     * @return user
     */
    public Optional<User> findUserById(int id) {
        return userRepo.findById(id);
    }

    /**
     * Find user detail by user id
     * 
     * @param id user id
     * @return user detail
     */
    public Optional<UserDetail> findUserDetailByUserId(int id) {
        return userDetailRepo.findById(id);
    }

    public Optional<User> login(LoginRequest loginRequest) {
        if (isLoginRequestEmpty(loginRequest)) {
            return Optional.empty();
        }

        Optional<User> optional = findUserByEmail(loginRequest.email());
        if (optional.isPresent()) {
            User user = optional.get();
            String password = sha256Password(loginRequest.password(), user.getSalt());
            if (password.equals(user.getPasswordHashValue())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    // check loginRequest field is empty or not
    private boolean isLoginRequestEmpty(LoginRequest loginRequest) {
        return !StringUtils.hasText(loginRequest.email()) || !StringUtils.hasText(loginRequest.password());
    }

    private String sha256Password(String password, String salt) {
        return DigestUtils.sha256Hex(salt + password);
    }
}
