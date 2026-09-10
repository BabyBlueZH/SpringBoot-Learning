package com.example.learnspringboot;

//Mapper 接口（Java 接口，定义增删改查方法）
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    // insert / deleteById / updateById / selectById / selectList / selectPage ...
    // 上周写的 @Select/@Insert/@Update/@Delete 全部删掉！

    // 自定义方法保留（复杂查询走 XML，MP 不拦你）
    // BaseMapper<User> 已经内置：selectList、selectById、insert、
    //List<User> search(@Param("id") Long id, @Param("username") String username);
    //删掉search，改在controller里面用LambdaQueryWrapper

}
