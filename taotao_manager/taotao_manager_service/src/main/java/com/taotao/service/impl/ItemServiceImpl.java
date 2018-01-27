package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {

//        设置分页
        PageHelper.startPage(page, rows);//只有第一个查询的才会分页
//        查询所有
        List<TbItem> tbItems = tbItemMapper.selectByExample(null);

        //构建分页对象
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<TbItem>(tbItems);
        //获取总记录数
        long total = tbItemPageInfo.getTotal();
        //创建EasyUIDataGridResult对象,封装total,rows属性
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setRows(tbItems);
        easyUIDataGridResult.setTotal(total);
        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult saveItem(TbItem tbItem, String desc) {
        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全TbItem对象属性 商品状态: 1正常 2下架 3删除
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //向商品表插入数据(如果为空就不插入)
        tbItemMapper.insertSelective(tbItem);
        //创建TbItemDesc对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全TbItemDesc属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //向商品描述表插入数据(如果为空就不插入)
        tbItemDescMapper.insertSelective(tbItemDesc);
        //返回
        return TaotaoResult.ok();
    }
}
