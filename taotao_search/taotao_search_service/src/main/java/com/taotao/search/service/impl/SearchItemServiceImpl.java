package com.taotao.search.service.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SearchDao searchDao;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importAllResult() throws Exception {
        //从数据库中查询所有商品的数据,导入数据到索引库中,用solrJ
        //1.创建连接对象solrserver,交由spring管理
        //2.调用mapper方法,返回值是List<SearchItem>
        List<SolrInputDocument> documents = new ArrayList<>();
        List<SearchItem> searchItems = searchItemMapper.getSearchItemList();
        //3.遍历
        for (SearchItem searchItem : searchItems) {
            //4.将集合中的数据 searchItem 放入document中
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_desc", searchItem.getItem_desc());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_title", searchItem.getTitle());
            documents.add(document);
        }
        //获取总记录数
        long total = solrServer.query(new SolrQuery()).getResults().getNumFound();


        //添加文档到索引库中
        solrServer.add(documents);
        solrServer.commit();
        return TaotaoResult.ok(total);
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {

        //创建查询的对象SolrQuery
        SolrQuery solrQuery = new SolrQuery();
        //设置主查询条件
        if(StringUtils.isNotBlank(queryString)){
            solrQuery.setQuery(queryString);
        }else{
            solrQuery.setQuery("*:*");
        }
        //3.设置过滤条件

        //3.1设置分页查询的条件
        if(page==null) page=1;
        if(page<1) page=1;
        if(rows==null) rows=60;

        solrQuery.setStart((page-1) * rows);//(page-1) * rows

        solrQuery.setRows(rows);
        //3.2设置默认的搜索域

        solrQuery.set("df", "item_keywords");

        //3.3 开启高亮 设置高亮显示的域 设置前缀和后缀
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        //4.注入dao  调用dao的方法
        SearchResult result = searchDao.search(solrQuery);

        //5.设置searchresult 对象  返回

        long pageCount = result.getRecordCount()/rows;

        if( result.getRecordCount()% rows>0){
            pageCount++;
        }
        result.setPageCount(pageCount );


        return result;
    }
}
