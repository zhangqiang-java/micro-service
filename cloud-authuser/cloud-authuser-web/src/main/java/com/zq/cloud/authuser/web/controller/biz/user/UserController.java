package com.zq.cloud.authuser.web.controller.biz.user;

import com.github.pagehelper.Page;
import com.zq.cloud.authuser.core.user.dto.*;
import com.zq.cloud.authuser.core.user.service.UserService;
import com.zq.cloud.authuser.dal.model.User;
import com.zq.cloud.dto.result.PageResult;
import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.starter.web.context.UserContext;
import com.zq.cloud.utils.BusinessAssertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/biz/authuser/user")
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 创建用户
     *
     * @return
     */
    @PostMapping("/create")
    @ApiOperation("创建用户")
    public ResultBase<Void> crate(@Validated @RequestBody UserCreateDto createDto) {
        userService.crate(createDto);
        return ResultBase.success();
    }


    /**
     * 修改用户
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改用户")
    public ResultBase<Void> update(@Validated @RequestBody UserUpdateDto updateDto) {
        UserContext.CurrentUser currentUser = UserContext.get().getUser();
        BusinessAssertUtils.isTrue(currentUser.getIsAdmin() || currentUser.getId().equals(updateDto.getId()),
                "没有足够的修改权限");
        userService.update(updateDto);
        return ResultBase.success();
    }


    /**
     * 停启用用户 只能
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("停启用用户")
    public ResultBase<Void> enable(@Validated @RequestBody UserEnableDto startStopDto) {
        UserContext.CurrentUser currentUser = UserContext.get().getUser();
        BusinessAssertUtils.isTrue(currentUser.getIsAdmin(), "只有管理员能够停启用用户");
        BusinessAssertUtils.isFalse(currentUser.getId().equals(startStopDto.getId()), "不能停启用自己");
        userService.enable(startStopDto);
        return ResultBase.success();
    }


    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("/findPage")
    @ApiOperation("分页查询")
    public PageResult<UserVo> findPage(@Validated @RequestBody UserPageRequestDto pageRequestDto) {
        return userService.findPage(pageRequestDto);
    }
}
