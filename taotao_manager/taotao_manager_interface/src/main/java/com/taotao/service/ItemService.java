package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    EasyUIDataGridResult getItemList(Integer page, Integer rows);

    TaotaoResult saveItem(TbItem tbItem, String desc);

    TbItem getTbItemById(Long id);

    void deleteTbItemById(Long id);

    void updateTbItem(TbItem tbItem,String desc);
}
