package com.mjoys.springboot.track.biz.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by leiliang on 2017/3/30.
 */
public class BaseDao {
    protected Logger logger                   = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("sqlSessionTemplate")
    protected SqlSessionTemplate sqlSession;

    protected SqlSessionTemplate getSqlSession() {
        return sqlSession;
    }

    protected String getStamentNameSpace(String method) {
        return getClass().getName() + "." + method;
    }
}
