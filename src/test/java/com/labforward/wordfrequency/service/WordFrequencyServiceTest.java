package com.labforward.wordfrequency.service;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;
import com.labforward.wordfrequency.exception.InvalidTargetWordException;
import com.labforward.wordfrequency.exception.TargetWordTooLongException;

 class WordFrequencyServiceTest {
  private WordFrequencyService wordFrequencyService;

  @BeforeEach
  void setup() {
    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
    wordFrequencyService = new DefaultWordFrequencyService(levenshteinDistance);
  }
  @Test
  void analyzeWordFrequency_invalidTargetWord() {
    WordFrequencyRequest request = new WordFrequencyRequest("Word Words Wor word", "Word Words");

    assertThrows(InvalidTargetWordException.class, () -> {
      wordFrequencyService.analyzeWordFrequency(request);
    });
  }

  @Test
  void analyzeWordFrequency_targetWordTooLong() {
    WordFrequencyRequest request = new WordFrequencyRequest("Word", "WordWords");

    assertThrows(TargetWordTooLongException.class, () -> {
      wordFrequencyService.analyzeWordFrequency(request);
    });
  }
  @Test
  void analyzeWordFrequency_whenEmptyNotebookEntry_thenVerifyException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new WordFrequencyRequest("", "word");
    });
    assertEquals("Notebook entry cannot be empty or null", exception.getMessage());
  }
  @Test
  void analyzeWordFrequency_whenEmptyTargetWord_thenVerifyException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new WordFrequencyRequest("Word word", "");
    });
    assertEquals("Target word cannot be empty or null", exception.getMessage());
  }
  @Test
  void analyzeWordFrequency_whenNullNotebookEntry_thenVerifyException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new WordFrequencyRequest(null, "word");
    });
    assertEquals("Notebook entry cannot be empty or null", exception.getMessage());
  }
  @Test
  void analyzeWordFrequency_whenNullTargetWord_thenVerifyException() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new WordFrequencyRequest("word", null);
    });
    assertEquals("Target word cannot be empty or null", exception.getMessage());
  }
  @Test
  void analyzeWordFrequency_whenValidInput_thenReturnWordFrequencyResponse() {
    WordFrequencyRequest request = new WordFrequencyRequest("Word Words Wor word", "Word");
    assertTrue(wordFrequencyService.analyzeWordFrequency(request) instanceof WordFrequencyResponse);
  }

  @Test
  void analyzeWordFrequency_whenValidInput_thenVerifyOutput() {
    WordFrequencyRequest request = new WordFrequencyRequest("Word Words Wor word", "Word");
    WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);

    assertEquals(1, response.frequency());
    assertTrue(response.similarWords().containsAll(List.of("Words", "Wor", "word")));
  }
   @ParameterizedTest
   @CsvSource({
       "'Word', 'Word', 1, 0",// for singleWord test
       "'word word WORD', 'word', 2, 0",// for caseInsensitivty
       "'I want to work LabForward', 'Forward', 0, 0"//for no match found
   })
   void analyzeWordFrequency_multipleScenarios_thenVerifyOutputs(
       String inputText, String targetWord, int expectedFrequency, int expectedSimilarWordsCount) {

     WordFrequencyRequest request = new WordFrequencyRequest(inputText, targetWord);

     WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);

     assertEquals(expectedFrequency, response.frequency(),
         () -> "Expected frequency mismatch for input: " + inputText + ", target: " + targetWord);
     assertEquals(expectedSimilarWordsCount, response.similarWords().size(),
         () -> "Expected similar words count mismatch for input: " + inputText + ", target: " + targetWord);
   }
  @Test
  void analyzeWordFrequency_whenWordWithSpecialCharacters_thenVerifyOutput() {
    WordFrequencyRequest request = new WordFrequencyRequest("Word! word.", "word");
    WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);

    assertEquals(0, response.frequency());
    assertTrue(response.similarWords().containsAll(List.of("word.")));
  }
  @Test
  void analyzeWordFrequency_whenLargeText_thenCorrectWordFrequency() throws IOException {
    String largeText = Files.readString(Path.of("src/test/resources/largeText.txt"));
    WordFrequencyRequest request = new WordFrequencyRequest(largeText, "data");
    WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);
    assertEquals(8, response.frequency());
    assertTrue(response.similarWords().containsAll(List.of("data.","data,","Data","date")));
  }
  @Test
  void analyzeWordFrequency_whenLargeTextHighSimilarity_thenVerifyWordFrequency() throws IOException {
    String largeText = Files.readString(Path.of("src/test/resources/repeatText.txt"));
    WordFrequencyRequest request = new WordFrequencyRequest(largeText, "tes");
    WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);
    assertEquals(0, response.frequency());
    assertTrue(response.similarWords().containsAll(List.of("test")));
  }
  @Test
  void analyzeWordFrequency_whenLargeTextWithRepeatedWords_thenVerifyWordFrequency() throws IOException {
    String largeText = Files.readString(Path.of("src/test/resources/repeatText.txt"));
    WordFrequencyRequest request = new WordFrequencyRequest(largeText, "test");
    WordFrequencyResponse response = wordFrequencyService.analyzeWordFrequency(request);
    assertEquals(10000, response.frequency());
  }

}
