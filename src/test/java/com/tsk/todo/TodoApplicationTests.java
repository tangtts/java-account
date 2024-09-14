package com.tsk.todo;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class TodoApplicationTests {

  @Resource
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void contextLoads() {
    String encode = bCryptPasswordEncoder.encode("123456");
    System.out.println(encode);
    boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$AY0LkLpqfSVvQ1KjYAUJ5uZozYBJQJlEHQI6.FbUBDgAN/ncCynam");
    System.out.println(matches);
  }

}
