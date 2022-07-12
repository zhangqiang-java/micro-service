package com.zq.cloud.authuser.core.auth.service.impl;

import com.zq.cloud.authuser.core.auth.dto.UserPermissionDto;
import com.zq.cloud.authuser.core.auth.service.AuthService;
import com.zq.cloud.authuser.core.constant.RedisKeyConstant;
import com.zq.cloud.authuser.dal.enums.ResourceTypeEnum;
import com.zq.cloud.authuser.dal.mapper.ResourceMapper;
import com.zq.cloud.authuser.dal.mapper.UserMapper;
import com.zq.cloud.authuser.dal.model.Resource;
import com.zq.cloud.authuser.dal.model.User;
import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 校验用户 token权限和请求的路径权限
     *
     * @param checkDto
     * @return
     */
    public UserPermissionDto checkUserPermission(UserPermissionCheckDto checkDto) {
        String userId = stringRedisTemplate.opsForValue().get(RedisKeyConstant.getUserTokenKey(checkDto.getToken()));
        BusinessAssertUtils.notLongin(StringUtils.isBlank(userId), "token不存在或已过期");
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(userId));
        BusinessAssertUtils.isTrue(Objects.nonNull(user) && user.getIsAvailable(), "用户不存在或已无效", userId);
        List<Resource> resourceByUserIdAndType = new ArrayList<>();
        if (user.getIsAdmin()) {
            resourceByUserIdAndType = resourceMapper.findAllInterface();
        } else {
            //菜单权限
            List<Resource> allMenuResource = resourceMapper.findResourceByUserIdAndType(user.getId(), ResourceTypeEnum.MENU.code(), Boolean.TRUE);
            //具体接口权限
            if (!CollectionUtils.isEmpty(allMenuResource)) {
                Set<Long> menuResourceIdSet = allMenuResource.stream().map(Resource::getId).collect(Collectors.toSet());
                resourceByUserIdAndType = resourceMapper.findByParentIdSet(menuResourceIdSet, ResourceTypeEnum.INTERFACE.code(), Boolean.TRUE);
            }

        }
        BusinessAssertUtils.notEmpty(resourceByUserIdAndType, "用户没有该接口访问权限");

        Set<String> interfaceUrlSet = resourceByUserIdAndType.stream().map(Resource::getUrl).collect(Collectors.toSet());
        boolean match = interfaceUrlSet.stream().anyMatch(interfaceUrl -> new AntPathMatcher().match(interfaceUrl, checkDto.getPath()));
        BusinessAssertUtils.isTrue(match, "用户没有该接口访问权限", user.getId(), checkDto.getPath());

        // 重置redis token失效时间
        stringRedisTemplate.expire(RedisKeyConstant.getUserTokenKey(checkDto.getToken()), 2, TimeUnit.HOURS);

        //返回数据
        UserPermissionDto userPermissionDto = new UserPermissionDto();
        userPermissionDto.setId(user.getId());
        userPermissionDto.setNickname(user.getNickname());
        userPermissionDto.setMobile(user.getMobile());
        userPermissionDto.setEmail(user.getEmail());
        userPermissionDto.setRealName(user.getRealName());
        return userPermissionDto;
    }


}
