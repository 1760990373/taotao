package com.taotao.search.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;


    @Autowired
    private SearchItemService SearchItemService;

    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page,
                         Model model) throws Exception {
        //1.引入搜索服务
        //2.注入服务
        //3.调用 获取到了数据（被分页的） 数据传递到页面中
        //解决get中文乱码问题
        queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
        SearchResult result = SearchItemService.search(queryString, page, ITEM_ROWS);
        //${query}

        model.addAttribute("query", queryString);
        //${totalPages}
        model.addAttribute("totalPages", result.getPageCount());
        //${itemList} 商品的列表
        model.addAttribute("itemList", result.getItemList());
        //${page}
        model.addAttribute("page", page);

        return "search";
    }


}
