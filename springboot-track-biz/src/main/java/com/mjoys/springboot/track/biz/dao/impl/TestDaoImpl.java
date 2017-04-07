package com.mjoys.springboot.track.biz.dao.impl;

import com.mjoys.springboot.track.biz.dao.BaseDao;
import com.mjoys.springboot.track.biz.dao.TestDao;
import com.mjoys.springboot.track.biz.dto.TestDto;
import org.springframework.stereotype.Repository;

/**
 * Created by leiliang on 2017/3/30.
 */
@Repository
public class TestDaoImpl extends BaseDao implements TestDao {
    @Override
    public int insert(TestDto testDto) {
        return getSqlSession().insert(getStamentNameSpace("insert"), testDto);
    }

    @Override
    public int update(TestDto testDto) {
        return getSqlSession().update(getStamentNameSpace("update"), testDto);
    }

    @Override
    public TestDto selectById(Long id) {
        return getSqlSession().selectOne(getStamentNameSpace("selectById"), id);
    }
}
