package com.example.learnspringboot;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public class Student {//加校验注解

    @Id//这里写id的意思是告诉SpringBoot这个字段是数据库的主键
    private Long id;

    @NotBlank(message = "姓名不能为空")//新增：name 不能为 null 或空字符串
    private String name;

    @NotNull(message = "分数不能为空")//新增：score 不能为 null

    @Min(value = 0, message = "分数不能小于 0")//新增：最低 0 分


    @Max(value = 100, message = "分数不能大于 100")//新增：最高 100 分
    private Double score;

    @Phone
    private String phone;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
