package com.mjoys.springboot.track.biz.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by leiliang on 2017/3/30.
 */
public class TestDto extends BaseDto{

    private static final long serialVersionUID = 1468732344070278793L;

    /* 名称 */
    private String name;

    /* 年龄 */
    private Integer age;

    public TestDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public TestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

