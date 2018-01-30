package com.taotao.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController  //@Controller 和@ResponseBody的组合
public class ItemInterfaceController {

    @Autowired
    private  ItemService itemService;

    //增
    //@RequestMapping(value = "")


    //删


    //查 url:/rest/interface/item/{id}
    @RequestMapping(value = "/rest/interface/item/{id}", method = RequestMethod.GET)
    public ResponseEntity<TbItem> getTbItemById(@PathVariable Long id) {

        try {
            TbItem tbItem = itemService.getTbItemById(id);
           return  ResponseEntity.status(HttpStatus.OK).body(tbItem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //改
}
