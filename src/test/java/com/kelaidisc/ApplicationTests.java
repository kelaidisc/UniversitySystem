package com.kelaidisc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(FlywayTestConfig.class)
public class ApplicationTests {

  @Test
  void contextLoads() {

  }
}