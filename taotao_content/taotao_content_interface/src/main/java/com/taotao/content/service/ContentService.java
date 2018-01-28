package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

public interface ContentService {
    EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows);

    TaotaoResult saveContent(TbContent tbContent);

    List<TbContent> getContentListByCategoryId(Long categoryId);
}
