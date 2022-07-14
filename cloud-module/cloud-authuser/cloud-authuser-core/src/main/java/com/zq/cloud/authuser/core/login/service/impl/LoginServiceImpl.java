package com.zq.cloud.authuser.core.login.service.impl;

import com.zq.cloud.authuser.core.constant.RedisKeyConstant;
import com.zq.cloud.authuser.core.login.dto.LoginVerifyCodeDto;
import com.zq.cloud.authuser.core.login.dto.MobileLoginDto;
import com.zq.cloud.authuser.core.login.dto.PasswordLoginDto;
import com.zq.cloud.authuser.core.login.service.LoginService;
import com.zq.cloud.authuser.core.user.util.PasswordUtil;
import com.zq.cloud.authuser.dal.mapper.PasswordAccountMapper;
import com.zq.cloud.authuser.dal.mapper.UserMapper;
import com.zq.cloud.authuser.dal.model.PasswordAccount;
import com.zq.cloud.authuser.dal.model.User;
import com.zq.cloud.utils.BusinessAssertUtils;
import com.zq.cloud.utils.RSAEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PasswordAccountMapper accountMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${zq.rsa.privateKey}")
    private String privateKey;

    /**
     * 手机验证码登录
     *
     * @param mobileLoginDto
     * @return
     */
    public String mobileLogin(MobileLoginDto mobileLoginDto) {
        //校验短信验证码

        //
        User byMobile = userMapper.findByMobile(mobileLoginDto.getMobile());
        BusinessAssertUtils.notNull(byMobile, "账号不存在，请联系管理员添加");
        BusinessAssertUtils.isTrue(byMobile.getIsAvailable(), "账号状态异常，请联系管理员");
        return this.generateLoginToken(byMobile.getId());
    }


    /**
     * 账号密码登录
     *
     * @param loginDto
     * @return
     */
    public String passwordLogin(PasswordLoginDto loginDto) {
        //todo 校验验证码
        PasswordAccount byAccountName = accountMapper.findByAccountName(loginDto.getUserName());
        BusinessAssertUtils.notNull(byAccountName, "用户不存在");
        User user = userMapper.selectByPrimaryKey(byAccountName.getUserId());
        BusinessAssertUtils.notNull(user, "用户不存在");

        //解密密码
        String passwordSecurity = RSAEncryptUtil.decryptByPrivateKey(privateKey, loginDto.getPassword());
        //比较密码
        String password = PasswordUtil.generate(passwordSecurity, byAccountName.getSalt());
        BusinessAssertUtils.isTrue(password.equals(byAccountName.getPassword()), "账号或密码错误");

        return this.generateLoginToken(user.getId());
    }


    /**
     * 发送手机验证码
     *
     * @param verifyCodeDto
     */
    public void sendLoginVerifyCode(LoginVerifyCodeDto verifyCodeDto) {
        //todo 检查验证码

        String mobileVerifySendCountKey = RedisKeyConstant.getMobileVerifySendCountKey(verifyCodeDto.getMobile());
        Long sendCount = stringRedisTemplate.opsForValue().increment(mobileVerifySendCountKey);
        stringRedisTemplate.expire(mobileVerifySendCountKey, 30, TimeUnit.MINUTES);
        BusinessAssertUtils.isTrue(sendCount <= 5, "获取短信验证码次数过多,请半个小时后重试", mobileVerifySendCountKey, sendCount);

        //todo 发送验证码
    }

    /**
     * 生成登录token
     *
     * @param userId
     * @return
     */
    private String generateLoginToken(Long userId) {
        //生成随机token
        String token = UUID.randomUUID().toString();
        String tokenKey = RedisKeyConstant.getUserTokenKey(token);
        stringRedisTemplate.opsForValue().set(tokenKey, String.valueOf(userId), 1, TimeUnit.HOURS);
        return token;
    }

}
