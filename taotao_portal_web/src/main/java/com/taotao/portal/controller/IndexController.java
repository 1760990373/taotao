package com.taotao.portal.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Value("${CATEGORY_ID}")
    private Long CATEGORY_ID;

    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;

    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;

    @Value("${AD1_WIDTHB}")
    private String AD1_WIDTHB;

    @Value("${AD1_HEIGHTB}")
    private String AD1_HEIGHTB;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        List<Ad1Node> ad1Nodes = new ArrayList<>();
        //转成需要在页面显示的pojo对象
        List<TbContent> tbContents = contentService.getContentListByCategoryId(CATEGORY_ID);
        for (TbContent tbContent : tbContents) {
            Ad1Node ad1Node = new Ad1Node();
            ad1Node.setAlt(tbContent.getSubTitle());
            ad1Node.setHref(tbContent.getUrl());

            ad1Node.setSrc(tbContent.getPic());
            ad1Node.setWidth(AD1_WIDTH);
            ad1Node.setHeight(AD1_HEIGHT);

            ad1Node.setSrcB(tbContent.getPic2());
            ad1Node.setWidthB(AD1_WIDTHB);
            ad1Node.setHeightB(AD1_HEIGHTB);

            ad1Nodes.add(ad1Node);
        }
        //将pojo对象转换成json返回页面
        String json = JsonUtils.objectToJson(ad1Nodes);
        model.addAttribute("ad1",json);
        return "index";
    }
}
