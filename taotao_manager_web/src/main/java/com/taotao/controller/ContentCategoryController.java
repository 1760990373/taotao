package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    //显示所有
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return contentCategoryService.getContentCategoryList(parentId);
    }

    //添加
    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaotaoResult saveContentCategory(TbContentCategory tbContentCategory) {
        return contentCategoryService.saveContentCategory(tbContentCategory);
    }

    //删除
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public TaotaoResult deleteContentCategory(@RequestParam(value = "id") Long id) {
        return contentCategoryService.deleteContentCategory(id);
    }


}
