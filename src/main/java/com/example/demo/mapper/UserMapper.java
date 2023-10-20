package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.dao.UserDao;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDao> {
    @Select("select * from user")
    List<UserDao> finall();

   @Insert("insert into user (username,password,email) values (#{userName},#{passWord},#{email})")
    public int register(UserDao user);

    @Update("update user set username = #{userName},password=#{passWord},email=#{email} where id = #{id}")
    public int update(UserDao user);

    @Delete("delete from user where id = #{id}")
    public int deleteById(@Param("id") Integer id);

    @Select("select * from user where id = #{id}")
    public UserDao login(@Param("id") Integer id);

}
