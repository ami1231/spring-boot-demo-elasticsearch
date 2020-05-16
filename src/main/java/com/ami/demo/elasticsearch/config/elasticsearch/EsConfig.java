package com.ami.demo.elasticsearch.config.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class EsConfig {

    /**
     * 創建es client端
     * @return
     */
    @Bean
    public RestHighLevelClient highLevelClient(){
        ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("127.0.0.1").build();
        return RestClients.create(clientConfiguration).rest();
    }

}
