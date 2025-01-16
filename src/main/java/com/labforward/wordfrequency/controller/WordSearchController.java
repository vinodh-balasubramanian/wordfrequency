package com.labforward.wordfrequency.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;
import com.labforward.wordfrequency.service.WordFrequencyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * REST Controller for handling word frequency analysis and similar word search.
 * <p>
 * This controller provides an endpoint to analyze the frequency of a target word
 * within a notebook entry and find similar words based on specific criteria.
 * </p>
 */
@RestController
public class WordSearchController {

  private final WordFrequencyService wordFrequencyService;
  private static final Logger logger = LoggerFactory.getLogger(WordSearchController.class);

  /**
   * Constructor to inject the {@link WordFrequencyService} dependency.
   *
   * @param wordFrequencyService the service used for word frequency analysis and similar word search.
   */
  public WordSearchController(WordFrequencyService wordFrequencyService) {
    this.wordFrequencyService = wordFrequencyService;
  }

  /**
   * Analyzes the frequency of a target word and finds similar words in the provided notebook entry.
   *
   * @param request the {@link WordFrequencyRequest} containing the notebook entry and target word.
   * @return a {@link ResponseEntity} containing the {@link WordFrequencyResponse} with analysis results.
   */
  @Operation(summary = "Analyze word frequency and find similar words in a notebook entry", description = "Accepts a notebook entry text and a target word to analyze its frequency and find similar words.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful analysis of word frequency", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WordFrequencyResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json"))})
  @PostMapping("/api/word-frequency")
  public ResponseEntity<WordFrequencyResponse> getWordFrequency(
      @Valid @RequestBody WordFrequencyRequest request) {
    logger.info("WordSearchController:getWordFrequency execution started.");
    WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);
    logger.info("WordSearchController:getWordFrequency execution finished.");
    return ResponseEntity.ok(response);
  }

}
