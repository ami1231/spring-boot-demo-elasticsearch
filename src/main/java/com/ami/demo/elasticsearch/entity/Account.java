package com.ami.demo.elasticsearch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "account")
public class Account {
    @Id
    private String id;
    private String name;
}
