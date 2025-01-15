package com.labforward.wordfrequency.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class WordSearchControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void givenValidRequest_whenPostToApi_thenReturnsWordFrequencyResponse() throws Exception {
    mockMvc.perform(post("/api/word-frequency")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"noteBookEntry\":\"Word Words Wor word\", \"targetWord\":\"Word\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.frequency").value(1))
        .andExpect(jsonPath("$.similarWords").isArray());
  }
  @Test
  void givenInvalidRequest_whenPostToApi_thenReturnsBadRequest() throws Exception {
    mockMvc.perform(post("/api/word-frequency")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"noteBookEntry\":\"Word Words Wor word\", \"targetWord\":\"Word Words\"}"))
        .andExpect(status().isBadRequest());
  }
}
