package com.taotao.controller;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexImportController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("index/importall")
    @ResponseBody
    public TaotaoResult importAllIndex() throws Exception {
        return searchItemService.importAllResult();
    }
}
