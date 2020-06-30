package com.ami.demo.elasticsearch.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "user")
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String sex;
}