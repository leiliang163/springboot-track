package com.mjoys.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 创 建 人 : leiliang.<br/>
 * 创建时间 : 2017/4/7 17:25.<br/>
 * 功能描述 : .<br/>
 * 变更记录 : .<br/>
 */
@Controller
@RequestMapping("/freemarkerTest")
public class FreemarkerTestController {

    private static final String MESSAGE = "Hello World";

    @RequestMapping("/hello")
    public String welcome(ModelMap model) {
        model.put("time", new Date());
        model.put("message", MESSAGE);
        return "mail/hello";
    }

    @RequestMapping("/hello1")
    public String welcome1(ModelMap model) {
        model.put("time", new Date());
        model.put("message", MESSAGE);
        return "hello";
    }

    @RequestMapping("/hello2")
    public String welcome2(ModelMap model) {
        model.put("time", new Date());
        model.put("message", MESSAGE);
        return "index";
    }

}
