package com.labforward.wordfrequency.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.labforward.wordfrequency.dto.WordFrequencyRequest;
import com.labforward.wordfrequency.dto.WordFrequencyResponse;
import com.labforward.wordfrequency.service.WordFrequencyService;

@ExtendWith(MockitoExtension.class)
class WordSearchControllerTest {

  @InjectMocks
  private WordSearchController controller;

  @Mock
  private WordFrequencyService service;

  @Test
  void givenValidRequest_whenPostToApi_thenReturnsExpectedResponse() {
    WordFrequencyRequest request = new WordFrequencyRequest("Word Words Wor word", "Word");
    WordFrequencyResponse response = new WordFrequencyResponse("Word",1, List.of("Words", "Wor", "word"));

    Mockito.when(service.analyzeWordFrequency(request)).thenReturn(response);

    ResponseEntity<WordFrequencyResponse> result = controller.getWordFrequency(request);

    Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    Assertions.assertEquals(1, result.getBody().frequency());
  }

}
