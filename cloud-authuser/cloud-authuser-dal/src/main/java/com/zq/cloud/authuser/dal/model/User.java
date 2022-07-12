package com.zq.cloud.authuser.dal.model;

import com.zq.cloud.starter.mybatis.model.BaseVersionModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "user_ass")
public class User extends BaseVersionModel {
    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 用户昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 用户头像id
     */
    @Column(name = "head_portrait_id")
    private Long headPortraitId;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 是否有效 0无效 1有效
     */
    @Column(name = "is_available")
    private Boolean isAvailable;


    /**
     * 是否是管理员 0不是 1是管理员
     */
    @Column(name = "is_admin")
    private Boolean isAdmin;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 上次登录IP
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 上次登录地址
     */
    @Column(name = "last_login_address")
    private String lastLoginAddress;

    /**
     * 当次登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 当次登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * 当次登录归属地
     */
    @Column(name = "login_address")
    private String loginAddress;

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
     * 获取用户昵称
     *
     * @return nickname - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    /**
     * 获取头像资源id
     *
     * @return headPortraitId - 头像资源id
     */
    public Long getHeadPortraitId() {
        return headPortraitId;
    }

    /**
     * 设置头像资源id
     *
     * @param headPortraitId 头像资源id
     */
    public void setHeadPortraitId(Long headPortraitId) {
        this.headPortraitId = headPortraitId;
    }


    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取是否有效 0无效 1有效
     *
     * @return is_available - 是否有效 0无效 1有效
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * 设置是否有效 0无效 1有效
     *
     * @param isAvailable 是否有效 0无效 1有效
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }


    /**
     * 获取是否是管理员 0不是 1是
     *
     * @return isAdmin - 是否是管理员 0不是 1是
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * 设置是否是管理员 0不是 1是
     *
     * @param isAdmin 是否是管理员 0不是 1是
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
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
     * 获取上次登录IP
     *
     * @return last_login_ip - 上次登录IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置上次登录IP
     *
     * @param lastLoginIp 上次登录IP
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取上次登录地址
     *
     * @return last_login_address - 上次登录地址
     */
    public String getLastLoginAddress() {
        return lastLoginAddress;
    }

    /**
     * 设置上次登录地址
     *
     * @param lastLoginAddress 上次登录地址
     */
    public void setLastLoginAddress(String lastLoginAddress) {
        this.lastLoginAddress = lastLoginAddress;
    }

    /**
     * 获取当次登录时间
     *
     * @return login_time - 当次登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置当次登录时间
     *
     * @param loginTime 当次登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取当次登录IP
     *
     * @return login_ip - 当次登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置当次登录IP
     *
     * @param loginIp 当次登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取当次登录归属地
     *
     * @return login_address - 当次登录归属地
     */
    public String getLoginAddress() {
        return loginAddress;
    }

    /**
     * 设置当次登录归属地
     *
     * @param loginAddress 当次登录归属地
     */
    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nickname=").append(nickname);
        sb.append(",headPortraitId=").append(headPortraitId);
        sb.append(", birthday=").append(birthday);
        sb.append(", address=").append(address);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", realName=").append(realName);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append(", isAdmin=").append(isAdmin);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", lastLoginIp=").append(lastLoginIp);
        sb.append(", lastLoginAddress=").append(lastLoginAddress);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", loginIp=").append(loginIp);
        sb.append(", loginAddress=").append(loginAddress);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}