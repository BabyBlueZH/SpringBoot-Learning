package com.example.learnspringboot;

import org.springframework.data.annotation.Id;

public class Student {

    @Id//这里写id的意思是告诉SpringBoot这个字段是数据库的主键
    private Long id;
    private String name;
    private Double score;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}
