package com.taotao.search.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //只要是有@RequestMapping修饰的方法都能被捕获到
public class ExceptionResolverController {

    @ExceptionHandler(value = Exception.class)
    public String showError(Model model){
        model.addAttribute("message","您的网络异常");
        return "error/exception";
    }
}
