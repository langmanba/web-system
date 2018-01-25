package com.web.portal.center.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QueryMapper {

    @Select("select age from user where name = #{name}")
    String getAge(@Param("name") String name);
}
