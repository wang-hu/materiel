package com.demo.springboot.mapper;

import com.demo.springboot.domain.security.MenuAuth;

public interface MenuAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuAuth record);

    int insertSelective(MenuAuth record);

    MenuAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuAuth record);

    int updateByPrimaryKey(MenuAuth record);
}