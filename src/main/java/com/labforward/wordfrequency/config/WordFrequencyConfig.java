package com.labforward.wordfrequency.config;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WordFrequencyConfig {

  @Bean
  public LevenshteinDistance levenshteinDistance() {
    return new LevenshteinDistance();
  }
}
