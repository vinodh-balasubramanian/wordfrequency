package com.labforward.wordfrequency.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.labforward.wordfrequency.constant.MessageConstants;
import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;
import com.labforward.wordfrequency.exception.InvalidTargetWordException;
import com.labforward.wordfrequency.exception.TargetWordTooLongException;
import com.labforward.wordfrequency.service.WordFrequencyService;
import com.labforward.wordfrequency.service.strategy.SimilarityStrategy;

/**
 * Implementation of {@link WordFrequencyService} that provides methods
 * to analyze the frequency of a target word in a given notebook entry
 * and identify similar words based on a similarity strategy.
 */
@Service
public class DefaultWordFrequencyService implements WordFrequencyService {

  private final SimilarityStrategy similarityStrategy;
  private static final Logger logger = LoggerFactory.getLogger(DefaultWordFrequencyService.class);

  /**
   * Constructor to inject the similarity strategy dependency.
   *
   * @param similarityStrategy the strategy used for calculating word similarity
   */
  public DefaultWordFrequencyService(SimilarityStrategy similarityStrategy) {
    this.similarityStrategy = similarityStrategy;
  }

  /**
   * Analyzes the frequency of the target word in the notebook entry
   * and finds similar words using the configured similarity strategy.
   *
   * @param request the request containing the notebook entry and target word
   * @return a {@link WordFrequencyResponse} containing the frequency and similar words
   */
  @Override
  public WordFrequencyResponse analyzeWordFrequency(WordFrequencyRequest request) {
    long startTime = System.currentTimeMillis();
    String noteBookEntry = request.noteBookEntry();
    String targetWord = request.targetWord();
    logger.info("analyzeWordFrequency: Target word = {}, Notebook entry length = {}", targetWord,
        noteBookEntry.length());
    validateInputs(targetWord, noteBookEntry);
    int frequency = calculateFrequency(noteBookEntry, targetWord);
    List<String> similarWords = findSimilarWords(targetWord, noteBookEntry);
    long endTime = System.currentTimeMillis();
    logger.info("analyzeWordFrequency finished. Total time taken in mill sec {}", (endTime - startTime));
    return new WordFrequencyResponse(request.targetWord(), frequency, similarWords);
  }

  /**
   * Validates the input parameters for correctness.
   *
   * @param targetWord    the word to be analyzed
   * @param notebookEntry the text in which the word is analyzed
   * @throws InvalidTargetWordException if the target word contains more than one word
   * @throws TargetWordTooLongException if the target word is longer than the notebook entry
   */
  private void validateInputs(String targetWord, String notebookEntry) {
    logger.debug("validateInputs started");
    if (targetWord.split("\\s+").length > 1) {
      logger.error("Target word '{}' contains more than one word.", targetWord);
      throw new InvalidTargetWordException(MessageConstants.TARGET_WORD_INVALID);
    }

    if (targetWord.length() > notebookEntry.length()) {
      logger.error("Target word '{}' cannot be longer than the notebook entry.", targetWord);
      throw new TargetWordTooLongException(MessageConstants.TARGET_WORD_TOO_LONG);
    }
    logger.debug("validateInputs ended");
  }

  /**
   * Calculates the frequency of the target word in the notebook entry.
   *
   * @param entryText  the notebook entry text
   * @param targetWord the word to count
   * @return the frequency of the target word in the notebook entry
   */
  private int calculateFrequency(String entryText, String targetWord) {
    logger.debug("calculateFrequency started");
    int frequency = (int)Arrays.stream(entryText.split("\\s+"))
        .filter(word -> word.equals(targetWord))
        .count();
    logger.debug("calculateFrequency started");
    return frequency;
  }

  /**
   * Identifies similar words to the target word from the notebook entry
   * using the configured similarity strategy.
   *
   * @param targetWord the target word
   * @param entryText  the notebook entry text
   * @return a list of words similar to the target word
   */
  private List<String> findSimilarWords(String targetWord, String entryText) {
    logger.debug("findSimilarWords started");
    Set<String> words = new HashSet<>(Arrays.asList(entryText.split("\\s+")));
    List<String> similarWords = words.stream()
        .filter(word -> similarityStrategy.calculate(targetWord, word) == 1)
        .toList();
    logger.debug("findSimilarWords ended");
    return similarWords;
  }
}
