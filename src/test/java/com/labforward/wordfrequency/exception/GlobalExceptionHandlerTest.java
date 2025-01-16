package com.labforward.wordfrequency.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.labforward.wordfrequency.constant.MessageConstants;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void givenInvalidTargetWordException_whenPostToApi_thenReturnsBadRequest() throws Exception {
    mockMvc.perform(post("/api/word-frequency").contentType(MediaType.APPLICATION_JSON)
            .content("{\"noteBookEntry\":\"Word Words Wor word\", \"targetWord\":\"Invalid Target\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value(MessageConstants.BAD_REQUEST))
        .andExpect(jsonPath("$.messages").value(MessageConstants.TARGET_WORD_INVALID))
        .andExpect(jsonPath("$.path").value("/api/word-frequency"));
  }

  @Test
  void givenTargetWordTooLongException_whenPostToApi_thenReturnsBadRequest() throws Exception {
    mockMvc.perform(post("/api/word-frequency").contentType(MediaType.APPLICATION_JSON)
            .content("{\"noteBookEntry\":\"Short\", \"targetWord\":\"ThisIsALongTargetWord\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value(MessageConstants.BAD_REQUEST))
        .andExpect(jsonPath("$.messages").value(MessageConstants.TARGET_WORD_TOO_LONG))
        .andExpect(jsonPath("$.path").value("/api/word-frequency"));
  }

  @Test
  void givenValidationException_whenPostToApi_thenReturnsBadRequest() throws Exception {
    mockMvc.perform(post("/api/word-frequency").contentType(MediaType.APPLICATION_JSON)
            .content("{\"notebookEntry\":\"\", \"targetWord\":\"Word\"}"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value(MessageConstants.BAD_REQUEST))
        .andExpect(jsonPath("$.path").value("/api/word-frequency"));
  }

  @Test
  void givenIllegalArgumentException_whenPostToApi_thenReturnsInternalServerError() throws Exception {
    mockMvc.perform(post("/api/word-frequency").contentType(MediaType.APPLICATION_JSON)
            .content("{\"noteBookEntry\":\"Word\", \"targetWord\":\"Word woo word\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value(MessageConstants.BAD_REQUEST))
        .andExpect(jsonPath("$.messages").isArray())
        .andExpect(jsonPath("$.path").value("/api/word-frequency"));
  }

}

