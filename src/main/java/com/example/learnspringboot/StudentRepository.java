package com.example.learnspringboot;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
//这个是继承了CrudRepository接口的，用于增删改查
public interface StudentRepository extends CrudRepository<Student, Long> {

    // 根据分数下限查学生（Spring Data JDBC 自动实现）
    List<Student> findByScoreGreaterThanEqualOrderByScoreDesc(Double score);
}
