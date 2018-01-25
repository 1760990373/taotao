package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    EasyUIDataGridResult getItemList(Integer page,Integer rows);
    TaotaoResult saveItem(TbItem tbItem,String desc);
}
