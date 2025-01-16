package com.labforward.wordfrequency.service;

import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;

/**
 * A service interface that provides methods
 * to analyze the frequency of a target word in a given notebook entry
 * and identify similar words based on a similarity strategy.
 */
public interface WordFrequencyService {

  /**
   * Analyzes the frequency of the target word in the notebook entry
   * and finds similar words using the configured similarity strategy.
   *
   * @param request the request containing the notebook entry and target word
   * @return a {@link WordFrequencyResponse} containing the frequency and similar words
   */
  WordFrequencyResponse analyzeWordFrequency(WordFrequencyRequest request);
}
