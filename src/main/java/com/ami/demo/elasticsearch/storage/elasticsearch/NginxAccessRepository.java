package com.ami.demo.elasticsearch.storage.elasticsearch;

import com.ami.demo.elasticsearch.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NginxAccessRepository extends ElasticsearchRepository<User,Long> {
}
