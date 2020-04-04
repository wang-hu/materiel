package com.demo.springboot.mapper;

import com.demo.springboot.domain.security.UserInfo;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    /**
     * 根据登录名查询用户信息
     * @param loginName 登录名
     * @return 用户信息
     */
    UserInfo selectByLoginName(String loginName);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}