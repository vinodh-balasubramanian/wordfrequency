package com.labforward.wordfrequency.service;

import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;

public interface WordFrequencyService {
  WordFrequencyResponse analyzeWordFrequency(WordFrequencyRequest request);
}
