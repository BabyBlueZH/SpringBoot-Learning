package com.example.learnspringboot;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController 的意思是：把这个类标记为一个 RESTful API 控制器。
// 有了它，类里面的方法就能处理 HTTP 请求并返回 JSON 数据。
@RestController

// @RequestMapping("/api/students") 的意思是：
// 这个控制器里所有方法的 URL 都以 /api/students 开头。
// 比如 @GetMapping 就处理 GET /api/students
@RequestMapping("/api/students")
public class StudentController {

    // 把 StudentRepository 注入进来，用来操作数据库里的 student 表
    private final StudentRepository studentRepository;

    // 构造函数：Spring 会自动把 studentRepository 传进来（依赖注入）
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ==================== 查全部 / 按分数筛选 ====================
    // @GetMapping 的意思是：这个方法处理 GET 请求。
    // 访问方式：
    //   GET /api/students        → 查全部
    //   GET /api/students?minScore=80  → 查分数 >= 80 的学生
    @GetMapping
    public Result<List<Student>> list(@RequestParam(required = false) Double minScore) {
        // @RequestParam(required = false) 的意思是：
        // 从 URL 参数里取 minScore 的值，如果没传就是 null
        if (minScore != null) {
            // 如果传了 minScore，按分数筛选
            return Result.success(studentRepository.findByScoreGreaterThanEqualOrderByScoreDesc(minScore));
        }
        // 没传 minScore，查全部
        return Result.success((List<Student>) studentRepository.findAll());
    }

    // ==================== 查单个 ====================
    // @PathVariable 的意思是：从 URL 路径里取 {id} 的值。
    // GET /api/students/1  → id = 1
    // GET /api/students/3  → id = 3
    @GetMapping("/{id}")
    public Result<Student> getById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "学生不存在，id: " + id));
        return Result.success(student);
    }

    // ==================== 新增 ====================
    // @PostMapping 的意思是：这个方法处理 POST 请求。
    // @RequestBody 的意思是：从 HTTP 请求体里读取 JSON，自动转换成 Student 对象
    // POST /api/students  + 请求体 {"name":"小李","score":95}
    @PostMapping
    public Result<Student> add(@RequestBody @Valid Student student) {
        // save 是 CrudRepository 自带的，新增一条数据
        Student saved = studentRepository.save(student);
        return Result.success(saved);
    }

    // ==================== 更新 ====================
    // PUT /api/students/1  + 请求体 {"name":"小张","score":88}
    // PUT 是全量更新：传来的字段会覆盖数据库里的全部字段
    @PutMapping("/{id}")
    public Result<Student> update(@PathVariable Long id, @RequestBody @Valid Student student) {
        // 先查一下，不存在就抛业务异常（全局异常处理器会接住）
        studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(404, "学生不存在，id: " + id));
        // 设置 id 确保更新的是这条记录
        student.setId(id);
        Student saved = studentRepository.save(student);
        return Result.success(saved);
    }

    // ==================== 删除 ====================
    // @DeleteMapping 的意思是：这个方法处理 DELETE 请求。
    // DELETE /api/students/1  → 删除 id=1 的学生
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        // 先检查存在不存在
        if (!studentRepository.existsById(id)) {
            throw new BusinessException(404, "学生不存在，id: " + id);
        }
        // deleteById 是 CrudRepository 自带的，根据主键删除
        studentRepository.deleteById(id);
        return Result.success();
    }
}
