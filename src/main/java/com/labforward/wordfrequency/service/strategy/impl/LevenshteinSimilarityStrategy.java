package com.labforward.wordfrequency.service.strategy.impl;

import org.apache.commons.text.similarity.LevenshteinDistance;

import com.labforward.wordfrequency.service.strategy.SimilarityStrategy;

/**
 * Implementation of the `SimilarityStrategy` interface using the Levenshtein distance algorithm.
 * <p>
 * This class calculates the similarity between two strings based on the Levenshtein distance.
 * </p>
 */
public class LevenshteinSimilarityStrategy implements SimilarityStrategy {

  private final LevenshteinDistance levenshteinDistance;

  public LevenshteinSimilarityStrategy() {
    this.levenshteinDistance = LevenshteinDistance.getDefaultInstance();
  }

  /**
   * Calculates the Levenshtein distance between two strings, representing the number of edits required
   * to transform one string into the other.
   *
   * @param source The first string to compare.
   * @param target The second string to compare.
   * @return The Levenshtein distance between the two strings.
   */
  @Override
  public int calculate(String source, String target) {
    return levenshteinDistance.apply(source, target);
  }
}

