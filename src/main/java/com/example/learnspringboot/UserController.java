package com.example.learnspringboot;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//暴露接口
@RestController//将当前类中的方法返回值作为响应体响应给浏览器
public class UserController {
    private final UserMapper userMapper;//自动注入,不需要再手动new

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping//将当前方法返回值作为响应体响应给浏览器
    public Result<List<User>> list() {
        return Result.success(userMapper.findAll());
    };//返回结果为Result<List<User>>

    @PostMapping
    public Result<User> create(@RequestBody User user){
        userMapper.insert(user);//插入后user.getId()有值了（@Option回填）
        return Result.success(user);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        userMapper.update(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id){//根据id删除用户
        userMapper.deleteById(id);
        return Result.success();
    }
}
