package com.labforward.wordfrequency.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response containing word frequency and similar words")
public record WordFrequencyResponse(
    @Schema(description = "Target word given in the input", example = "word") String targetWord,
    @Schema(description = "Frequency of the target word in the notebook entry", example = "1") int frequency,
    @Schema(description = "List of similar words to the target word", example = "[\"Words\", \"Wor\", \"word\"]") List<String> similarWords) {
}
