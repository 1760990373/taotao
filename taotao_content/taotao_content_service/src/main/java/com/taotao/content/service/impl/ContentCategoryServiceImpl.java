package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
        //创建example
        TbContentCategoryExample example = new TbContentCategoryExample();
        //设置条件
        example.createCriteria().andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> contentCategories = tbContentCategoryMapper.selectByExample(example);
        //转成List<EasyUITreeNode>对象
        ArrayList<EasyUITreeNode> list = new ArrayList<>();
        //遍历
        for (TbContentCategory contentCategory : contentCategories) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(contentCategory.getId());
            easyUITreeNode.setText(contentCategory.getName());
            easyUITreeNode.setState(contentCategory.getIsParent() ? "closed" : "open");
            list.add(easyUITreeNode);
        }
        return list;
    }

    @Override
    public TaotaoResult saveContentCategory(TbContentCategory tbContentCategory) {
        //补全属性
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
        //获取父节点
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        //判断 : 如果新增的节点的父节点是叶子节点,更新父节点
        if (!parent.getIsParent()) {
            //设置父节点为父节点
            parent.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
        }
        tbContentCategoryMapper.insertSelective(tbContentCategory);
        return TaotaoResult.ok(tbContentCategory);
    }

    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        if (!tbContentCategory.getIsParent()) {
            //如果是叶子节点,直接删除
            tbContentCategoryMapper.deleteByPrimaryKey(id);
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
            return TaotaoResult.ok();
        } else {
            return TaotaoResult.build(404, "该分类无法删除!");
        }
    }


}
