package com.mjoys.springboot.track.biz.req;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by leiliang on 2017/3/30.
 */
public class ReqAddTest {
    /* 名称 */
    @NotBlank(message = "名称不可为空")
    private String name;

    /* 年龄 */
    @NotNull(message = "年龄不可为空")
    private Integer age;

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
