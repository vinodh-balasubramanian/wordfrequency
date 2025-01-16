package com.labforward.wordfrequency.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.labforward.wordfrequency.constant.MessageConstants;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents the request payload for analyzing word frequency and finding similar words.
 * <p>
 * This record encapsulates the notebook entry text and the target word,
 * along with validation and schema annotations for API documentation.
 * </p>
 *
 * @param noteBookEntry the text of the notebook entry to analyze, must not be null or blank.
 * @param targetWord    the target word to analyze frequency and find similar words, must not be null or blank.
 * @throws IllegalArgumentException if any field is null or blank.
 */
@Schema(description = "Request to analyze word frequency and find similar words")
public record WordFrequencyRequest(
    @NotBlank(message = MessageConstants.INVALID_NOTEBOOK_ENTRY) @Schema(description = "The text of the notebook entry to analyze", example = "Word Words Wor word") String noteBookEntry,
    @NotBlank(message = MessageConstants.EMPTY_TARGET_WORD) @Schema(description = "The target word to analyze frequency and find similar words", example = "Word") String targetWord) {
  private static final Logger logger = LoggerFactory.getLogger(WordFrequencyRequest.class);

  /**
   * Custom compact constructor to enforce additional validation.
   * <p>
   * Ensures that both {@code noteBookEntry} and {@code targetWord} are not null or blank.
   * </p>
   *
   * @throws IllegalArgumentException if either field is null or blank.
   */
  public WordFrequencyRequest {
    if (noteBookEntry == null || noteBookEntry.isBlank()) {
      logger.error("Invalid notebook entry: it cannot be null or blank.");
      throw new IllegalArgumentException(MessageConstants.INVALID_NOTEBOOK_ENTRY);
    }

    if (targetWord == null || targetWord.isBlank()) {
      logger.error("Invalid target word: it cannot be null or blank.");
      throw new IllegalArgumentException(MessageConstants.EMPTY_TARGET_WORD);
    }
  }
}
