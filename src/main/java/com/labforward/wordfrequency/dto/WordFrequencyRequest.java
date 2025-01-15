package com.labforward.wordfrequency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to analyze word frequency and find similar words")
public record WordFrequencyRequest(
    @NotBlank(message = "Notebook entry cannot be empty or null")
    @Schema(description = "The text of the notebook entry to analyze", example = "Word Words Wor word")
    String noteBookEntry,
    @NotBlank(message = "Target word cannot be empty or null")
    @Schema(description = "The target word to analyze frequency and find similar words", example = "Word")
    String targetWord) {
    public WordFrequencyRequest {
        if (noteBookEntry == null || noteBookEntry.isBlank()) {
            throw new IllegalArgumentException("Notebook entry cannot be empty or null");
        }

        if (targetWord == null || targetWord.isBlank()) {
            throw new IllegalArgumentException("Target word cannot be empty or null");
        }
    }
}
