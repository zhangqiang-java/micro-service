package com.zq.cloud.authuser.dal.mapper;

import com.zq.cloud.authuser.dal.model.PasswordAccount;
import tk.mybatis.mapper.common.Mapper;

public interface PasswordAccountMapper extends Mapper<PasswordAccount> {
    default PasswordAccount findByAccountName(String accountName) {
        PasswordAccount account = new PasswordAccount();
        account.setAccountName(accountName);
        return this.selectOne(account);
    }
}