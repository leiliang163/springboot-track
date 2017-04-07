package com.mjoys.springboot.track.biz.service;


import com.mjoys.springboot.track.biz.dto.TestDto;

/**
 * Created by leiliang on 2017/3/30.
 */
public interface TestService {

    boolean insert(TestDto testDto);


    boolean update(TestDto testDto);


    TestDto selectById(Long id);
}
