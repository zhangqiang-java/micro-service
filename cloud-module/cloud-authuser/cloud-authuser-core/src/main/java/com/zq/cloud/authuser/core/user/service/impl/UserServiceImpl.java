package com.zq.cloud.authuser.core.user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zq.cloud.authuser.core.user.dto.*;
import com.zq.cloud.authuser.core.user.service.UserService;
import com.zq.cloud.authuser.core.user.util.PasswordUtil;
import com.zq.cloud.authuser.core.user.util.UserConvertDtoUtil;
import com.zq.cloud.authuser.dal.dto.UserSearchDto;
import com.zq.cloud.authuser.dal.mapper.PasswordAccountMapper;
import com.zq.cloud.authuser.dal.mapper.UserMapper;
import com.zq.cloud.authuser.dal.model.PasswordAccount;
import com.zq.cloud.authuser.dal.model.User;
import com.zq.cloud.dto.result.PageResult;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.SnowFlakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户接口
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordAccountMapper passwordAccountMapper;

    /**
     * 分页查询
     *
     * @return
     */
    public PageResult<UserVo> findPage(UserPageRequestDto pageRequestDto) {
        UserSearchDto searchDto = UserConvertDtoUtil.coverToSearchDto(pageRequestDto);
        Page<User> page = PageHelper.startPage(pageRequestDto.getPage(), pageRequestDto.getPageSize())
                .doSelectPage(() -> userMapper.findPage(searchDto));

        List<UserVo> result = new ArrayList<>(page.getResult().size());
        page.getResult().forEach(user -> result.add(UserConvertDtoUtil.coverToUserVo(user)));
        return PageResult.from(page.getPageNum(), page.getPageSize(), result);
    }

    /**
     * 创建用户
     *
     * @param createDto
     */
    @Transactional
    public void crate(UserCreateDto createDto) {
        User byMobile = userMapper.findByMobile(createDto.getMobile());
        BusinessAssertUtils.isNull(byMobile, "手机号：【" + createDto.getMobile() + "】已存在");
        User user = UserConvertDtoUtil.convertToCrateUser(createDto);
        userMapper.insert(user);

        //生成默认的账号和密码 账号为手机号码  密码为手机号后六位
        String salt = PasswordUtil.getRandomSalt();
        String password = PasswordUtil.generate((createDto.getMobile().substring(createDto.getMobile().length() - 6)), salt);
        PasswordAccount passwordAccount = new PasswordAccount();
        passwordAccount.setId(SnowFlakeUtils.getNextId());
        passwordAccount.setPassword(password);
        passwordAccount.setSalt(salt);
        passwordAccount.setAccountName(createDto.getMobile());
        passwordAccount.setUserId(user.getId());
        passwordAccountMapper.insert(passwordAccount);
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

            //修改了手机号 修改账户的账户名
            PasswordAccount byAccountName = passwordAccountMapper.findByAccountName(oldUser.getMobile());
            byAccountName.setAccountName(updateDto.getMobile());
            int updateSize = passwordAccountMapper.updateByPrimaryKey(byAccountName);
            BusinessAssertUtils.isTrue(updateSize > 0, "用户数据已经发生变化,请刷新重试");
        }

        UserConvertDtoUtil.convertToUpdateUser(updateDto, oldUser);
        int updateSize = userMapper.updateByPrimaryKey(oldUser);
        BusinessAssertUtils.isTrue(updateSize > 0, "用户数据已经发生变化,请刷新重试");
    }


    /**
     * 启停用户
     *
     * @param startStopDto
     */
    public void enable(UserEnableDto startStopDto) {
        User oldUser = userMapper.selectByPrimaryKey(startStopDto.getId());
        BusinessAssertUtils.notNull(oldUser, "用户不存在");

        if (!oldUser.getIsAvailable().equals(startStopDto.getAvailable())) {
            int updateSize = userMapper.updateByPrimaryKey(oldUser);
            BusinessAssertUtils.isTrue(updateSize > 0, "用户数据已经发生变化,请刷新重试");
        }
    }


}
