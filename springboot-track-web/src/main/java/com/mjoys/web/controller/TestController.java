/**
 * Project Name:media-deploy<br>
 * File Name:TestController.java<br>
 * Package Name:cn.com.duiba.tuia.media.web.controller<br>
 * Date:2016年12月28日下午4:48:21<br>
 * Copyright (c) 2016, duiba.com.cn All Rights Reserved.<br>
 */
package com.mjoys.web.controller;

import com.mjoys.springboot.track.api.model.Result;
import com.mjoys.springboot.track.biz.dto.TestDto;
import com.mjoys.springboot.track.biz.req.ReqAddTest;
import com.mjoys.springboot.track.biz.service.TestService;
import com.mjoys.web.aop.LogWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * ClassName: TestController <br/>
 * Function: 获取缓存. <br/>
 * date: 2016年12月28日 下午4:48:21 <br/>
 *
 * @author xiawei
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private TestService testService;

    /**
     * getAppNameById:(获取媒体名称缓存). <br/>
     *
     * @param id
     * @return
     * @author ZFZ
     * @since JDK 1.6
     */
    @ResponseBody
    @RequestMapping(value = "/selectById", method = RequestMethod.GET)
    public Result<TestDto> selectById(Long id) {
        testService.selectById(id);
        return successResult(testService.selectById(id));
    }

    @LogWrite(modelName = "测试模块", option = "插入", ignoreParams = { "passwd" })
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Boolean> add(@Valid ReqAddTest req, BindingResult result) {
        // 参数校验
        checkParam(result);

        // 新增
        testService.insert(new TestDto(req.getName(), req.getAge()));

        return successResult();
    }

    @LogWrite(modelName = "测试模块", option = "修改", ignoreParams = { "passwd" })
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<Boolean> update(@Valid ReqAddTest req, BindingResult result) {
        // 参数校验
        checkParam(result);

        // 更新
        testService.update(new TestDto(req.getName(), req.getAge()));

        return successResult();
    }


}
