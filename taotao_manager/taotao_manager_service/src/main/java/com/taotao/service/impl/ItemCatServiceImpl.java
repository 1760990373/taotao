package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {

//        创建Example对象
        TbItemCatExample example = new TbItemCatExample();

//        设置查询条件
        example.createCriteria().andParentIdEqualTo(parentId);
//        执行查询
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(example);
//        转成List<EasyUITreeNode>
        ArrayList<EasyUITreeNode> list = new ArrayList<>();
        for (TbItemCat tbItemCat : tbItemCats) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
//          state：如果该节点下有子节点则“closed”，如果没有子节则点“open”
            easyUITreeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");
            easyUITreeNode.setText(tbItemCat.getName());
            list.add(easyUITreeNode);
        }
        return list;
    }
}
