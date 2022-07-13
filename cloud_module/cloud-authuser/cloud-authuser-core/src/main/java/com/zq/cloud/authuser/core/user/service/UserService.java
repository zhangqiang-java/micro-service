package com.zq.cloud.authuser.core.user.service;

import com.zq.cloud.authuser.core.user.dto.*;
import com.zq.cloud.dto.result.PageResult;

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
    PageResult<UserVo> findPage(UserPageRequestDto pageRequestDto);

    /**
     * 启停用用户
     *
     * @param enableDto
     */
    void enable(UserEnableDto enableDto);
}
