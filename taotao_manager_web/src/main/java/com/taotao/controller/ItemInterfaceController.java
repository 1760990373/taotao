package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
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
    private ItemService itemService;

    //增
    /* url: /rest/interface/item
       method:POST
       参数:item商品数据
       返回值: 201 created
    */
    @RequestMapping(value = "/rest/interface/item", method = RequestMethod.POST)
    public ResponseEntity<Void> saveTbItem(TbItem tbItem, String desc) {
        try {
            itemService.saveItem(tbItem, desc);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //删
    /* url: /rest/interface/item/{id}
       method:DELETE
       参数:id
       返回值:204 no_content
    */
    @RequestMapping(value = "/rest/interface/item/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTbItemById(@PathVariable Long id) {

        try {
            itemService.deleteTbItemById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //查
    /* url: /rest/interface/item/{id}
       method:GET
       参数: id
       返回值:200 ok
    */
    @RequestMapping(value = "/rest/interface/item/{id}", method = RequestMethod.GET)
    public ResponseEntity<TbItem> getTbItemById(@PathVariable Long id) {

        try {
            TbItem tbItem = itemService.getTbItemById(id);
            return ResponseEntity.status(HttpStatus.OK).body(tbItem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //改
    /* url: /rest/interface/item
       method:PUT
       参数:item ,desc
       返回值:204 no_content
    */
    @RequestMapping(value = "/rest/interface/item", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateTbItem(TbItem tbItem, String desc) {
        try {
            itemService.updateTbItem(tbItem, desc);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
