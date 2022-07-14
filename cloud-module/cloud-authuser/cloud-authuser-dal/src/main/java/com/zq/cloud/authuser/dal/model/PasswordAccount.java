package com.zq.cloud.authuser.dal.model;

import com.zq.cloud.starter.mybatis.model.BaseVersionModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "password_account")
public class PasswordAccount extends BaseVersionModel {
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
    @Column(name = "account_name")
    private String accountName;

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
     * @return accountName - 登陆账号
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置登陆账号
     *
     * @param accountName 登陆账号
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountName=").append(accountName);
        sb.append(", password=").append(password);
        sb.append(", userId=").append(userId);
        sb.append(", salt=").append(salt);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}