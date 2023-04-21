package com.kent.logindemo.model.bean;

public record RegisterRequest(String account, String email, String password, String confirmPassword) {
    
}
