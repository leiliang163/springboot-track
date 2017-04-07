package com.mjoys.springboot.track.biz.dao;

import com.mjoys.springboot.track.biz.dto.TestDto;

/**
 * Created by leiliang on 2017/3/30.
 */
public interface TestDao {

    int insert(TestDto testDto);


    int update(TestDto testDto);


    TestDto selectById(Long id);
}
