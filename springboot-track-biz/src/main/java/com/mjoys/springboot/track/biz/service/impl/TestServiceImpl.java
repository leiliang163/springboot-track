package com.mjoys.springboot.track.biz.service.impl;

import com.mjoys.springboot.track.biz.dao.TestDao;
import com.mjoys.springboot.track.biz.dto.TestDto;
import com.mjoys.springboot.track.biz.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by leiliang on 2017/3/30.
 */
@Service
public class TestServiceImpl extends BaseService implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public boolean insert(TestDto testDto) {
        return testDao.insert(testDto) == 1? true:false;
    }

    @Override
    public boolean update(TestDto testDto) {
        return testDao.update(testDto) == 1? true:false;
    }

    @Override
    public TestDto selectById(Long id) {

        return testDao.selectById(id);
    }
}
