package com.zq.cloud.authuser.core.user.util;

import com.zq.cloud.authuser.core.user.dto.UserCreateDto;
import com.zq.cloud.authuser.core.user.dto.UserPageRequestDto;
import com.zq.cloud.authuser.core.user.dto.UserUpdateDto;
import com.zq.cloud.authuser.core.user.dto.UserVo;
import com.zq.cloud.authuser.dal.dto.UserSearchDto;
import com.zq.cloud.authuser.dal.model.User;
import com.zq.cloud.utils.SnowFlakeUtils;

/**
 * 用户实体转换工具类
 */
public class UserConvertDtoUtil {

    /**
     * 创建实体dto转换
     *
     * @param createDto
     * @return
     */
    public static User convertToCrateUser(UserCreateDto createDto) {
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
        user.setIsAdmin(Boolean.FALSE);
        return user;
    }


    /**
     * 修改实体dto转换
     *
     * @param updateDto
     * @return
     */
    public static void convertToUpdateUser(UserUpdateDto updateDto, User oldUser) {
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
    public static UserSearchDto coverToSearchDto(UserPageRequestDto pageRequestDto) {
        UserSearchDto userSearchDto = new UserSearchDto();
        userSearchDto.setNickname(pageRequestDto.getNickname());
        userSearchDto.setAddress(pageRequestDto.getAddress());
        userSearchDto.setMobile(pageRequestDto.getMobile());
        userSearchDto.setRealName(pageRequestDto.getRealName());
        return userSearchDto;
    }

    /**
     * 展示实体dto转换
     *
     * @param user
     * @return
     */
    public static UserVo coverToUserVo(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setNickname(user.getNickname());
        userVo.setHeadPortraitId(user.getHeadPortraitId());
        userVo.setBirthday(user.getBirthday());
        userVo.setAddress(user.getAddress());
        userVo.setMobile(user.getMobile());
        userVo.setEmail(user.getEmail());
        userVo.setRealName(user.getRealName());
        userVo.setIsAvailable(user.getIsAvailable());
        userVo.setLastLoginTime(user.getLastLoginTime());
        userVo.setLastLoginIp(user.getLastLoginIp());
        userVo.setLastLoginAddress(user.getLastLoginAddress());
        userVo.setLoginTime(user.getLoginTime());
        userVo.setLoginIp(user.getLoginIp());
        userVo.setLoginAddress(user.getLoginAddress());
        return userVo;
    }
}
