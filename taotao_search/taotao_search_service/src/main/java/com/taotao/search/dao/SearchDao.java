package com.taotao.search.dao;


import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    //注入SolrServer
    @Autowired
    private SolrServer solrServer;

    //根据查询的条件执行查询,返回结果集(包括分页的数据和商品的列表)

    public SearchResult search(SolrQuery solrQuery) throws Exception {
        //根据SolrQuery对象的条件执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取结果集 SolrDocumentList --> SearchItem ---->searchResult
        SolrDocumentList results = response.getResults();

        List<SearchItem> SearchItems = new ArrayList<>();
        //取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument result : results) {
            SearchItem searchItem = new SearchItem();
            //商品id
            searchItem.setId(Long.valueOf((String)result.get("id")));
            //商品类别
            searchItem.setCategory_name(result.get("item_category_name").toString());
            searchItem.setImage(result.get("item_image").toString());
            //searchItem.setItem_desc(result.get("item.desc").toString());
            searchItem.setPrice((Long)result.get("item_price"));
            searchItem.setSell_point(result.get("item_sell_point").toString());

            List<String> list = highlighting.get(result.get("id")).get("item_title");
            if (list != null && list.size() > 0) {
                searchItem.setTitle(list.get(0));
            } else {
                searchItem.setTitle(result.get("item_title").toString());
            }

            SearchItems.add(searchItem);

        }
        long numFound = results.getNumFound();

        //设置总记录数,设置商品列表
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        searchResult.setItemList(SearchItems);

        return searchResult;
    }
}
