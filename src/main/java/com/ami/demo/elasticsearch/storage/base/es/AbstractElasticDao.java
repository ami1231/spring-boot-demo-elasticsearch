package com.ami.demo.elasticsearch.storage.base.es;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractElasticDao {

    //分批查询数量
    protected static final int BATCH_SCAN_ONE_TIME = 8000;

    protected static final int DEFAULT_TIME_VALUE = 20000;

    protected static final int FLUSH_BYTES = 5 * 1024 * 1024; //5M

    protected ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 根据搜索返回进行数据转换
     * @param searchResponse es搜寻结果
     * @param values  返回物件
     * @param resultFunction 转换function
     * @param <RT>
     */
    protected <RT> List<RT> getResultFromResp(SearchResponse searchResponse, List<RT> values, Function<Map<String, Object>, RT> resultFunction){
        SearchHits searchHits = searchResponse.getHits();
        if(searchHits!=null){
            for(SearchHit searchHit:searchHits.getHits()){
                values.add(resultFunction.apply(searchHit.getSourceAsMap()));
            }
        }
        return values;
    }

    /**
     * 查询index下所有数据
     * @param allIndex
     * @param fields
     * @param queryBuilder
     * @param resultFunction
     * @param <RT>
     * @return
     */
    public <RT> List<RT> queryAll(String[] allIndex, String[] fields, QueryBuilder queryBuilder, Function<Map<String, Object>, RT> resultFunction){
        List<RT> values = new LinkedList<>();
        //取得es-client
        Client esClient = elasticsearchTemplate.getClient();
        //创建查询物件
        SearchRequestBuilder searchRequestBuilder = esClient
                .prepareSearch(allIndex)
                .setSearchType(SearchType.DEFAULT)
                .setQuery(queryBuilder)
                .setSize(BATCH_SCAN_ONE_TIME)
                .setScroll(new TimeValue(DEFAULT_TIME_VALUE));
        //指定字段的时候，只查询这些
        if(fields!=null && fields.length>0){
            searchRequestBuilder.setFetchSource(fields,null);
        }

        //查询第一批次
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        //取得总笔数
        long totalHits = searchResponse.getHits().getTotalHits().value;
        int page = (int) (totalHits / BATCH_SCAN_ONE_TIME);

        //转换第一批物件
        getResultFromResp(searchResponse,values,resultFunction);

        for(int i = 0 ;i<page;i++){
            //根据分页进行后续转换
            //根据ScrollId查下一批次
            searchResponse = esClient.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(DEFAULT_TIME_VALUE))
                    .execute().actionGet();
            getResultFromResp(searchResponse,values,resultFunction);
        }
        return values;
    }


}
