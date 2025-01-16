package com.labforward.wordfrequency.service.strategy;

/**
 * A strategy interface for calculating the similarity between two strings.
 * Implementations of this interface define specific algorithms for
 * determining how similar two strings are, typically by returning a
 * numerical value representing the similarity measure.
 */
public interface SimilarityStrategy {

  /**
   * Calculates the similarity between the source and target strings.
   *
   * @param source the source string to compare; must not be null
   * @param target the target string to compare; must not be null
   * @return a numerical value representing the similarity between the two strings.
   */
  int calculate(String source, String target);
}
