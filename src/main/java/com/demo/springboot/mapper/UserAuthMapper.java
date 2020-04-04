package com.demo.springboot.mapper;

import com.demo.springboot.domain.security.UserAuth;

import java.util.List;

public interface UserAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    UserAuth selectByPrimaryKey(Integer id);

    /**
     * 查询用户角色
     * @param loginName 登录名
     * @return 用户角色列表
     */
    List<UserAuth> listUserAuthorities(String loginName);

    int updateByPrimaryKeySelective(UserAuth record);

    int updateByPrimaryKey(UserAuth record);
}