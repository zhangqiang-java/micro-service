package com.zq.cloud.authuser.dal.model;

import com.zq.cloud.starter.mybatis.model.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "password_account")
public class PasswordAccount extends BaseModel {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 登陆账号
     */
    @Column(name = "username")
    private String username;

    /**
     * 登陆密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 盐加密
     */
    @Column(name = "salt")
    private String salt;

    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 是否启用 0未启动 1启动
     */
    @Column(name = "is_available")
    private Boolean isAvailable;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取登陆账号
     *
     * @return username - 登陆账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登陆账号
     *
     * @param username 登陆账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取登陆密码
     *
     * @return password - 登陆密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登陆密码
     *
     * @param password 登陆密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取盐加密
     *
     * @return salt - 盐加密
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐加密
     *
     * @param salt 盐加密
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取上次登录时间
     *
     * @return last_login_time - 上次登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置上次登录时间
     *
     * @param lastLoginTime 上次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取是否启用 0未启动 1启动
     *
     * @return is_available - 是否启用 0未启动 1启动
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * 设置是否启用 0未启动 1启动
     *
     * @param isAvailable 是否启用 0未启动 1启动
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", userId=").append(userId);
        sb.append(", salt=").append(salt);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}