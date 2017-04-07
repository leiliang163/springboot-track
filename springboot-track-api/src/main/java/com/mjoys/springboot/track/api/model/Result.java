/**
 * 文件名： Result.java 此类描述的是： 作者: leiliang 创建时间: 2016年3月23日 上午11:00:27
 */
package com.mjoys.springboot.track.api.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * <一句话功能描述> <功能详细描述>.
 *
 * @param <T> the generic type
 * @author: leiliang
 * @version:
 */
public class Result<T> implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1467576157657126613L;

    /** The code. */
    private String            code;

    /** The message. */
    private String            desc;

    /** The t. */
    private transient T       data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
