package com.labforward.wordfrequency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.labforward.wordfrequency.service.strategy.SimilarityStrategy;
import com.labforward.wordfrequency.service.strategy.impl.LevenshteinSimilarityStrategy;

/**
 * This is a config class to inject LevenshteinSimilarityStrategy
 **/
@Configuration
public class WordFrequencyConfig {

  @Bean
  public SimilarityStrategy similarityStrategy() {
    return new LevenshteinSimilarityStrategy();
  }
}
