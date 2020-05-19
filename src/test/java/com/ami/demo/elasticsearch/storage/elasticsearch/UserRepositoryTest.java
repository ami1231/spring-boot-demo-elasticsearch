package com.ami.demo.elasticsearch.storage.elasticsearch;

import com.ami.demo.elasticsearch.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Test
    public void testUserDaoInsert(){
        User user = User.builder().age(10).name("ami").build();
        userRepository.save(user);
    }

    @Test
    public void testUserDaoQuery(){
        User user = User.builder().name("ami").build();
        user = userRepository.findByName(user.getName());
        System.out.println(user);
    }

    @Test
    public void testUserDaoDelete(){
        User user = User.builder().age(10).name("ami").build();
        userRepository.delete(user);
    }
}