package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.content.utils.JedisClient;
import com.taotao.content.utils.JedisClientPool;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${TB_CONTENT_KEY}")
    private String TB_CONTENT_KEY;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbContentMapper tbContentMapper;

    private static Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        //调用pageHelper分页
        PageHelper.startPage(page, rows);
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


    @Override
    public List<TbContent> getContentListByCategoryId(Long categoryId) {
        //判断redis是否存在该缓存
        //如果有,直接返回
        try {
            String stringJson = jedisClient.hget(TB_CONTENT_KEY, categoryId + "");
            if (StringUtils.isNotBlank(stringJson)) {
                //返回不为空,证明存在
                logger.info("已经有了缓存");
                return JsonUtils.jsonToList(stringJson, TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //如果没有,去数据库取数据,并添加缓存
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        List<TbContent> list = tbContentMapper.selectByExample(example);
        try {
            jedisClient.hset(TB_CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
            System.out.println("添加了缓存");
            logger.info("添加了缓存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
