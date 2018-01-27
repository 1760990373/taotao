package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
    EasyUIDataGridResult getContentList(Long categoryId,Integer page,Integer rows);

    TaotaoResult saveContent(TbContent tbContent);
}
