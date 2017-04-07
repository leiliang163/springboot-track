package com.mjoys.springboot.track.biz.dao;


import com.mjoys.springboot.track.biz.dto.OperateLogDto;

/**
 * Created by leiliang on 2017/3/31.
 */
public interface OperateLogDAO {
    /**
     * 新增操作日志.
     *
     * @param record the record
     * @return the int
     */
    int insert(OperateLogDto record);
}
