package com.ami.demo.elasticsearch.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.http.HttpHeaders;

import java.time.format.DateTimeFormatter;

@Configuration
public class EsConfig {


    @Bean
    public RestHighLevelClient restHighLevelClient(){
        HttpHeaders httpHeaders = new HttpHeaders();
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("127.0.0.1:9200")
              .build();
        return RestClients.create(clientConfiguration).rest();
    }

}
