package com.taotao.controller;

import com.taotao.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    @ResponseBody
    public String getNow() {
        return testService.getNow();
    }

}
