package com.labforward.wordfrequency;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class WordFrequencyApplicationTests {
  @Autowired private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    // The test will pass if the application context loads successfully
  }

  @Test
  void mainMethodTest() {
    WordFrequencyApplication.main(new String[] {});

    assertNotNull(applicationContext);
  }
}

