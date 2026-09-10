package com.example.learnspringboot;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//MyBatisPlus插件配置
@Configuration
public class MyBbatisPlusConfig {//告诉 MyBatis-Plus：以后遇到分页查询，要启用分页插件。
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //分页插件：自动拼limit+自动查count
        interceptor.addInnerInterceptor(
                new PaginationInnerInterceptor(DbType.MYSQL)//开启 MySQL 分页拦截器：
                // 处理：
                // LIMIT 分页，COUNT(*) 查询总条数，计算总页数，当前页等信息
        );
        return interceptor;
    }
}

//之前加的：
//mybatis-plus-jsqlparser依赖，就是给这个分页拦截器解析、改写 SQL 用的。
//MyBatis-Plus 配置
//        ↓
//PaginationInnerInterceptor
//        ↓
//真正的数据库分页