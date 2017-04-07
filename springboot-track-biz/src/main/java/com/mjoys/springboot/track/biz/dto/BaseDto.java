package com.mjoys.springboot.track.biz.dto;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leiliang on 2017/3/30.
 */
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 1468732344070278793L;

    /** The id. */
    protected Long            id;

    /** 创建时间. */
    protected Date gmtCreate;

    /** 最新更新时间. */
    protected Date            gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
