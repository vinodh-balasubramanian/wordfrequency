package com.labforward.wordfrequency.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents the response containing the word frequency and similar words.
 * <p>
 * This record is used to send the result of the word frequency analysis,
 * including the frequency count of the target word in the notebook entry and
 * a list of similar words based on the selected similarity criteria.
 * </p>
 *
 * @param targetWord The target word that was analyzed.
 * @param frequency The frequency of the target word in the notebook entry.
 * @param similarWords List of words similar to the target word based on the similarity calculation.
 */
@Schema(description = "Response containing word frequency and similar words")
public record WordFrequencyResponse(
    @Schema(description = "Target word given in the input", example = "word") String targetWord,
    @Schema(description = "Frequency of the target word in the notebook entry", example = "1") int frequency,
    @Schema(description = "List of similar words to the target word", example = "[\"Words\", \"Wor\", \"word\"]") List<String> similarWords) {
}
