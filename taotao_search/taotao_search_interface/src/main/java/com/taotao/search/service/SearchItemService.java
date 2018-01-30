package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;


public interface SearchItemService {

    TaotaoResult importAllResult() throws Exception;

    SearchResult search(String queryString, Integer page, Integer rows) throws Exception;

}
