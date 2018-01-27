package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        //调用pageHelper分页
        PageHelper.startPage(page,rows);
        //执行查询
        List<TbContent> list = tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        //封装到EasyUIDataGridResult
        EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
        easyUIDataGridResult.setTotal(pageInfo.getTotal());
        easyUIDataGridResult.setRows(pageInfo.getList());
        return easyUIDataGridResult;
    }

    @Override
    public TaotaoResult saveContent(TbContent tbContent) {
        //补全属性
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //调用方法
        tbContentMapper.insertSelective(tbContent);
        return TaotaoResult.ok();
    }
}
