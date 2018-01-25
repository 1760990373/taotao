package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/index")
    public String showIndex() {

        return "index";
    }

    @RequestMapping("/{page}")
    public String showItemList(@PathVariable(value = "page") String page) {
        return page;
    }

}
