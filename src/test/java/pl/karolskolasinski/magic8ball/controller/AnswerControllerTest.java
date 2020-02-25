package pl.karolskolasinski.magic8ball.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AnswerControllerTest {

    @Mock
    AnswerService answerService;

    @Mock
    SequenceGeneratorService sequenceGeneratorService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        AnswerController answerController = new AnswerController(answerService, sequenceGeneratorService);
        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
    }

    @Test
    void answer_shouldSaveAnswerToDBAndReturnStatusOk() throws Exception {
        String uri = "/addAnswer";
        Answer answer = new Answer(1, "Answer");
        String inputJson = mapToJson(answer);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON))
//                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String resultCZ = result.getResponse().getContentAsString();
        assertNotNull(resultCZ);
//        assertEquals(answer, resultCZ);
    }

    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}
