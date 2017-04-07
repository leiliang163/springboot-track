/**
 * 文件名： ResultPageDO.java 此类描述的是： 作者: leiliang 创建时间: 2016年3月23日 上午11:06:15
 */
package com.mjoys.springboot.track.api.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <一句话功能描述> <功能详细描述>.
 *
 * @param <T> the generic type
 * @author: leiliang
 * @version:
 */
public class ResultPage<T> implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -2762491186195007587L;

    /**
     * 错误码.
     */
    private String code;

    /**
     * 错误描述.
     */
    private String desc;

    /**
     * 分页查询count总数.
     */
    private long count;

    /**
     * 分页查询结果集.
     */
    private transient List<T> data;

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

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
