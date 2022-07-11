package com.zq.cloud.authuser.core.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zq.cloud.authuser.core.user.dto.UserCreateDto;
import com.zq.cloud.authuser.core.user.dto.UserPageRequestDto;
import com.zq.cloud.authuser.core.user.dto.UserStartStopDto;
import com.zq.cloud.authuser.core.user.dto.UserUpdateDto;
import com.zq.cloud.authuser.core.user.service.UserService;
import com.zq.cloud.authuser.dal.dto.UserSearchDto;
import com.zq.cloud.authuser.dal.mapper.UserMapper;
import com.zq.cloud.authuser.dal.model.User;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户接口
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询
     *
     * @return
     */
    public Page<User> findPage(UserPageRequestDto pageRequestDto) {
        UserSearchDto searchDto = this.coverToSearchDto(pageRequestDto);
        return PageHelper.startPage(pageRequestDto.getPage(), pageRequestDto.getPageSize())
                .doSelectPage(() -> userMapper.findPage(searchDto));
    }

    /**
     * 创建用户
     *
     * @param createDto
     */
    public void crate(UserCreateDto createDto) {
        User byMobile = userMapper.findByMobile(createDto.getMobile());
        BusinessAssertUtils.isNull(byMobile, "手机号：【" + createDto.getMobile() + "】已存在");
        userMapper.insert(this.convertToCrateUser(createDto));
    }


    /**
     * 修改用户
     *
     * @param updateDto
     */
    public void update(UserUpdateDto updateDto) {
        User oldUser = userMapper.selectByPrimaryKey(updateDto.getId());
        BusinessAssertUtils.notNull(oldUser, "用户不存在");
        if (!oldUser.getMobile().equals(updateDto.getMobile())) {
            User byMobile = userMapper.findByMobile(updateDto.getMobile());
            BusinessAssertUtils.isNull(byMobile, "手机号：【" + updateDto.getMobile() + "】已存在");
        }

        this.convertToUpdateUser(updateDto, oldUser);
        int updateSize = userMapper.updateByPrimaryKey(oldUser);
        BusinessAssertUtils.isTrue(updateSize > 0, "用户数据已经发生变化,请刷新重试");
    }


    /**
     * 启停用户
     *
     * @param startStopDto
     */
    public void enable(UserStartStopDto startStopDto) {
        User oldUser = userMapper.selectByPrimaryKey(startStopDto.getId());
        BusinessAssertUtils.notNull(oldUser, "用户不存在");

        if (!oldUser.getIsAvailable().equals(startStopDto.getAvailable())) {
            int updateSize = userMapper.updateByPrimaryKey(oldUser);
            BusinessAssertUtils.isTrue(updateSize > 0, "用户数据已经发生变化,请刷新重试");
        }
    }


    /**
     * 创建实体dto转换
     *
     * @param createDto
     * @return
     */
    private User convertToCrateUser(UserCreateDto createDto) {
        User user = new User();
        user.setId(SnowFlakeUtils.getNextId());
        user.setNickname(createDto.getNickname());
        user.setBirthday(createDto.getBirthday());
        user.setHeadPortraitId(createDto.getHeadPortrait());
        user.setAddress(createDto.getAddress());
        user.setMobile(createDto.getMobile());
        user.setEmail(createDto.getEmail());
        user.setRealName(createDto.getRealName());
        user.setIsAvailable(Boolean.TRUE);
        return user;
    }


    /**
     * 修改实体dto转换
     *
     * @param updateDto
     * @return
     */
    private void convertToUpdateUser(UserUpdateDto updateDto, User oldUser) {
        oldUser.setId(updateDto.getId());
        oldUser.setNickname(updateDto.getNickname());
        oldUser.setBirthday(updateDto.getBirthday());
        oldUser.setAddress(updateDto.getAddress());
        oldUser.setHeadPortraitId(updateDto.getHeadPortrait());
        oldUser.setMobile(updateDto.getMobile());
        oldUser.setEmail(updateDto.getEmail());
        oldUser.setRealName(updateDto.getRealName());
    }

    /**
     * 查询实体dto转换
     *
     * @param pageRequestDto
     * @return
     */
    private UserSearchDto coverToSearchDto(UserPageRequestDto pageRequestDto) {
        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setNickname(pageRequestDto.getNickname());
        userSearchDto.setAddress(pageRequestDto.getAddress());
        userSearchDto.setMobile(pageRequestDto.getMobile());
        userSearchDto.setRealName(pageRequestDto.getRealName());
        return userSearchDto;
    }
}
