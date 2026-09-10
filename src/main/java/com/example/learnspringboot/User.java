package com.example.learnspringboot;

import org.springframework.data.relational.core.mapping.Table;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user") // ★ 告诉 MP：这个类对应 user 表
public class User {// ★ 告诉 MP：id 是主键，且数据库自增

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
