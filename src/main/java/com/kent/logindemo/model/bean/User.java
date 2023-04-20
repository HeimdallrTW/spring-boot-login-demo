package com.kent.logindemo.model.bean;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_email", columnDefinition = "VARCHAR", unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "user_salt", columnDefinition = "VARCHAR", nullable = false)
    private String salt;

    @JsonIgnore
    @Column(name = "password_hash", columnDefinition = "VARCHAR", nullable = false)
    private String passwordHashValue;

    @Column(name = "user_name", columnDefinition = "VARCHAR", unique = true, nullable = false)
    private String userName;

    @Column(name = "is_admin", columnDefinition = "BIT")
    private boolean isAdmin;

    @Column(name = "is_verified", columnDefinition = "BIT")
    private boolean isVerified;

    @Column(name = "is_blocked", columnDefinition = "BIT")
    private boolean isblocked;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login", columnDefinition = "TIMESTAMP")
    private Date lastLogin;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at", columnDefinition = "DATETIME")
    private Date createdAt;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "modified_at", columnDefinition = "DATETIME")
    private Date modifiedAt;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade =  CascadeType.ALL)
    private UserDetail userDetail;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHashValue() {
        return passwordHashValue;
    }

    public void setPasswordHashValue(String passwordHashValue) {
        this.passwordHashValue = passwordHashValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isIsblocked() {
        return isblocked;
    }

    public void setIsblocked(boolean isblocked) {
        this.isblocked = isblocked;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
}
