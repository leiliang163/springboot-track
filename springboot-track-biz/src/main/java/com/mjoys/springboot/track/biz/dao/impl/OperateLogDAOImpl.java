package com.mjoys.springboot.track.biz.dao.impl;

import com.mjoys.springboot.track.biz.dao.BaseDao;
import com.mjoys.springboot.track.biz.dao.OperateLogDAO;
import com.mjoys.springboot.track.biz.dto.OperateLogDto;
import org.springframework.stereotype.Repository;

/**
 * Created by leiliang on 2017/3/31.
 */
@Repository
public class OperateLogDAOImpl extends BaseDao implements OperateLogDAO {

    @Override
    public int insert(OperateLogDto record) {
        return getSqlSession().insert(getStamentNameSpace("insert"), record);
    }
}
