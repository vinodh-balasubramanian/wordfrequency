package com.labforward.wordfrequency.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;
import com.labforward.wordfrequency.exception.InvalidTargetWordException;
import com.labforward.wordfrequency.exception.TargetWordTooLongException;

@Service
public class DefaultWordFrequencyService implements WordFrequencyService {

  private final LevenshteinDistance levenshteinDistance;
  private static final Logger logger = LoggerFactory.getLogger(DefaultWordFrequencyService.class);

  public DefaultWordFrequencyService(LevenshteinDistance levenshteinDistance) {
    this.levenshteinDistance = levenshteinDistance;
  }

  @Override
  public WordFrequencyResponse analyzeWordFrequency(WordFrequencyRequest request) {
    long startTime = System.currentTimeMillis();
    String noteBookEntry = request.noteBookEntry();
    String targetWord = request.targetWord();
    logger.info("Analyzing word frequency: Target word = {}, Notebook entry length = {}", targetWord, noteBookEntry.length());
    validateInputs(targetWord, noteBookEntry);
    int frequency = calculateFrequency(noteBookEntry, targetWord);
    List<String> similarWords = findSimilarWords(targetWord, noteBookEntry);
    long endTime = System.currentTimeMillis();
    logger.info("analyzeWordFrequency finished. Total time taken in mill sec {}", (endTime - startTime));
    return new WordFrequencyResponse(request.targetWord(), frequency, similarWords);
  }

  private void validateInputs(String targetWord, String notebookEntry) {
    if (targetWord.split("\\s+").length > 1) {
      logger.error("Target word '{}' contains more than one word.", targetWord);
      throw new InvalidTargetWordException("Target word cannot contain more than one word.");
    }

    if (targetWord.length() > notebookEntry.length()) {
      logger.error("Target word '{}' cannot be longer than the notebook entry.", targetWord);
      throw new TargetWordTooLongException("Target word cannot be longer than the notebook entry.");
    }
  }

  private int calculateFrequency(String entryText, String targetWord) {
    String[] words = entryText.split("\\s+");
    int frequency = 0;
    for (String word : words) {
      if (word.equals(targetWord)) {
        frequency++;
      }
    }
    return frequency;
  }

  private List<String> findSimilarWords(String targetWord, String entryText) {
    Set<String> words = new HashSet<>(Arrays.asList(entryText.split("\\s+")));
    List<String> similarWords = new ArrayList<>();
    for (String word : words) {
      if (!word.equals(targetWord) && levenshteinDistance.apply(targetWord, word) <= 1) {
        similarWords.add(word);
      }
    }
    return similarWords;
  }
}
