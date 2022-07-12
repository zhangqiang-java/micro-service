package com.zq.cloud.authuser.web.controller.biz.role;

import com.zq.cloud.authuser.core.role.dto.*;
import com.zq.cloud.authuser.core.role.service.RoleService;
import com.zq.cloud.dto.result.PageResult;
import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.dto.result.SingleResult;
import com.zq.cloud.starter.web.context.UserContext;
import com.zq.cloud.utils.BusinessAssertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biz/authuser/role")
@Api(tags = "角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 创建角色
     *
     * @return
     */
    @PostMapping("/create")
    @ApiOperation("创建角色")
    public ResultBase<Void> crate(@Validated @RequestBody RoleCreateDto createDto) {
        UserContext.CurrentUser currentUser = UserContext.get().getUser();
        BusinessAssertUtils.isTrue(currentUser.getIsAdmin(), "只有管理员能够创建角色");
        roleService.crate(createDto);
        return ResultBase.success();
    }


    /**
     * 修改角色
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("修改角色")
    public ResultBase<Void> update(@Validated @RequestBody RoleUpdateDto updateDto) {
        UserContext.CurrentUser currentUser = UserContext.get().getUser();
        BusinessAssertUtils.isTrue(currentUser.getIsAdmin(), "只有管理员能够修改角色");
        roleService.update(updateDto);
        return ResultBase.success();
    }


    /**
     * 停启用角色
     *
     * @return
     */
    @PostMapping("/update")
    @ApiOperation("停启用角色")
    public ResultBase<Void> enable(@Validated @RequestBody RoleEnableDto startStopDto) {
        UserContext.CurrentUser currentUser = UserContext.get().getUser();
        BusinessAssertUtils.isTrue(currentUser.getIsAdmin(), "只有管理员能够停启用角色");
        roleService.enable(startStopDto);
        return ResultBase.success();
    }


    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("/findPage")
    @ApiOperation("分页查询")
    public PageResult<RoleVo> findPage(@Validated @RequestBody RolePageRequestDto pageRequestDto) {
        pageRequestDto.setUserId(UserContext.get().getUser().getId());
        return roleService.findPage(pageRequestDto);
    }

    /**
     * 角色分配
     *
     * @return
     */
    @PostMapping("/assign")
    @ApiOperation("角色分配")
    public ResultBase<Void> assign(@Validated @RequestBody RoleAssignDto assignDto) {
        UserContext.CurrentUser currentUser = UserContext.get().getUser();
        BusinessAssertUtils.isTrue(currentUser.getIsAdmin(), "只有管理员能够分配角色");
        roleService.assign(assignDto);
        return ResultBase.success();
    }

    /**
     * 查询角色详情
     *
     * @param id
     * @return
     */
    @PostMapping("/assign/{id}")
    @ApiOperation("查询角色详情")
    public SingleResult<RoleInfoVo> findById(@PathVariable("id") Long id) {
        RoleInfoVo roleInfoVo = roleService.findById(id);
        return SingleResult.from(roleInfoVo);
    }
}
