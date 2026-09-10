package com.example.learnspringboot;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//暴露接口
@RestController//将当前类中的方法返回值作为响应体响应给浏览器
@RequestMapping("/api/users")
public class UserController {
    private final UserMapper userMapper;//自动注入,不需要再手动new

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping//将当前方法返回值作为响应体响应给浏览器
    //查全部selectList(null)，无条件
    public Result<List<User>> list() {
        return Result.success(userMapper.selectList(null));
    };//返回结果为Result<List<User>>

    @GetMapping("/{id}")//查单个
    public Result<User> get(@PathVariable Long id){
        return Result.success(userMapper.selectById(id));
    }
    @PostMapping
    public Result<User> create(@RequestBody User user){
        userMapper.insert(user);//插入后user.getId()有值了（@Option回填）
        return Result.success(user);
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id){//根据id删除用户
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping("/search")//根据id或username查询
    public Result<List<User>> search(@RequestParam(required = false) Long id,
                                     @RequestParam(required = false) String username){//根据id或username查询
        List<User> list = userMapper.selectList(new LambdaQueryWrapper<User>()
                .eq(id != null, User::getId, id)//如果 id 不为空，则添加条件；即id有值才拼接
                .like(username != null && !username.isEmpty(), User::getUsername, username)//username非空才拼接
                .orderByAsc(User::getId)//按id升序
        );
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<Page<User>> page(@RequestParam(defaultValue = "1") long page,
                                   @RequestParam(defaultValue = "2") long size){
        Page<User> result = userMapper.selectPage(new Page<>(page,size),null);
        return Result.success(result);
    }
}
