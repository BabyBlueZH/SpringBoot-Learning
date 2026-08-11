package com.example.learnspringboot;

//Mapper 接口（Java 接口，定义增删改查方法）

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAll();//查询所有用户

    @Select("select * from user where id=#{id}")
    User findById(int id);//根据id查询用户

    @Insert("insert into user(username, email) values(#{username}, #{email})")//插入用户
    @Options(useGeneratedKeys = true, keyProperty = "id")//获取插入数据的自增主键id
    int insert(User user);//插入用户

    @Update("UPDATE user set username = #{username}, email = #{email} where id = #{id}")
    int update(User user);

    @Delete("delete from user where id = #{id}")
    int deleteById(long id);
}
