package com.taotao.search.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class SolrJTest {
    @Test
    public void SolrJTest() throws SolrServerException {
        //创建连接对象
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.84.129:8081/solr");
        //创建查询对象
        SolrQuery solrQuery = new SolrQuery("*:*");

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");


        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取结果集
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        System.out.println("总记录数:"+numFound);

        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();


        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            List<String> list = highlighting.get(result.get("id")).get("item_title");
            if(list!=null&&list.size()>0){
                System.out.println(list.get(0));
            }else{
                System.out.println(result.get("item_title"));
            }
            System.out.println(result.get("item_price"));
            System.out.println(result.get("item_title"));

        }
    }

}
