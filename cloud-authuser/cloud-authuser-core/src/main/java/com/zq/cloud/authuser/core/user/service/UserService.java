package com.zq.cloud.authuser.core.user.service;

import com.github.pagehelper.Page;
import com.zq.cloud.authuser.core.user.dto.UserCreateDto;
import com.zq.cloud.authuser.core.user.dto.UserPageRequestDto;
import com.zq.cloud.authuser.core.user.dto.UserStartStopDto;
import com.zq.cloud.authuser.core.user.dto.UserUpdateDto;
import com.zq.cloud.authuser.dal.model.User;

public interface UserService {
    /**
     * 创建用户
     *
     * @param createDto
     */
    void crate(UserCreateDto createDto);

    /**
     * 修改用户
     *
     * @param createDto
     */
    void update(UserUpdateDto createDto);

    /**
     * 分页查询
     *
     * @param pageRequestDto
     * @return
     */
    Page<User> findPage(UserPageRequestDto pageRequestDto);

    /**
     * 启停用用户
     *
     * @param startStopDto
     */
    void enable(UserStartStopDto startStopDto);
}
