package com.kent.logindemo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kent.logindemo.model.bean.LoginRequest;
import com.kent.logindemo.model.bean.RegisterRequest;
import com.kent.logindemo.model.bean.User;
import com.kent.logindemo.model.bean.UserDetail;
import com.kent.logindemo.model.repository.UserDetailRepo;
import com.kent.logindemo.model.repository.UserRepo;

@Service
public class UserService {
    private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";

    private final UserRepo userRepo;
    private final UserDetailRepo userDetailRepo;
    private final MailService mailService;

    public UserService(UserRepo userRepo, UserDetailRepo userDetailRepo, MailService mailService) {
        this.userRepo = userRepo;
        this.userDetailRepo = userDetailRepo;
        this.mailService = mailService;
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
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        User user = optional.get();
        String password = sha256Password(loginRequest.password(), user.getSalt());
        if (!password.equals(user.getPasswordHashValue())) {
            return Optional.empty();
        }

        return optional;
    }

    // check loginRequest field is empty or not
    private boolean isLoginRequestEmpty(LoginRequest loginRequest) {
        return !StringUtils.hasText(loginRequest.email()) || !StringUtils.hasText(loginRequest.password());
    }

    public String generateSalt() {
        return RandomStringUtils.random(40, characters);
    }

    private String sha256Password(String password, String salt) {
        return DigestUtils.sha256Hex(salt + password);
    }

    public Optional<User> register(RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setUserName(registerRequest.account());
        newUser.setEmail(registerRequest.email());
        newUser.setSalt(generateSalt());
        newUser.setPasswordHashValue(sha256Password(registerRequest.password(), newUser.getSalt()));
        newUser.setEnabled(false);
        newUser.setVerificationCode(generateRandomToken());
        UserDetail newUserDetail = new UserDetail();
        newUserDetail.setUser(newUser);
        newUser.setUserDetail(newUserDetail);
        User save = userRepo.save(newUser);
        return Optional.of(save);
    }

    public boolean isRegisterRequestIsEmpty(RegisterRequest registerRequest) {
        return !StringUtils.hasText(registerRequest.email()) || !StringUtils.hasText(registerRequest.password());
    }

    public void sendVerificationEmail(User user) {
        String email = user.getEmail();
        String subject = "Verify your email";
        String content = "<p>Please click the link below to verify your email:<br/>"
                + "<a href=\"http://localhost:8787/user/verify?email=" + email
                + "&token=" + user.getVerificationCode()
                + "\">Verify</a></p>";
        mailService.sendEmail(email, subject, content);
    }

    public String generateRandomToken() {
        return RandomStringUtils.randomAlphanumeric(40);
    }

    @Modifying
    @Transactional
    public void enableAccout(String email, String token) {
        Optional<User> optional = findUserByEmail(email);
        if (optional.isEmpty()) {
            return;
        }
        User user = optional.get();
        if (user.getVerificationCode().equals(token)) {
            user.setEnabled(true);
        }
    }
}
