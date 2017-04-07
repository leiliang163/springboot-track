package com.mjoys.springboot.track.biz.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by leiliang on 2017/3/31.
 */
public class OperateLogDto extends BaseDto{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 用户ID. */
    private Long              userId;

    /** 用户类型. */
    private Integer           userType;

    /** 模块名称. */
    private String            modelName;

    /** 方法名称. */
    private String            option;

    /** 操作是否成功. */
    private Integer           isSuccess;

    /** 操作内容. */
    private String            content;

    /** 用户ip. */
    private String            userIp;

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
