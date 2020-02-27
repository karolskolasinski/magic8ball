package pl.karolskolasinski.magic8ball.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Answer Controller CRUD ▼ Test")
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

    String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    @DisplayName(">create")
    void answer_shouldSaveAnswerToDBAndReturnStatusOk() throws Exception {
        //given
        String uri = "/admin/addAnswer";
        int id = sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME);
        String answerContent = "Answer";
        Answer answer = new Answer(id, answerContent);
        String successAdding = "success adding: " + answer.toString();
        String inputJson = mapToJson(answer);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(successAdding, result);
    }

    @Test
    @DisplayName(">read [all]")
    void answer_shouldGetAllAnswersInToStringFormat() throws Exception {
        //given
        String uri = "/admin//getAllAnswers";
        int answer1Id = sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME);
        int answer2Id = sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME);
        String answer1Content = "Answer1";
        String answer2Content = "Answer1";
        Answer answer1 = new Answer(answer1Id, answer1Content);
        Answer answer2 = new Answer(answer2Id, answer2Content);
        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);
        given(answerService.findAllAnswers()).willReturn(answerList);

        //then
        MockHttpServletResponse response = mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String result = response.getContentAsString();
        assertNotNull(result);
        assertEquals(answerList.toString(), result);
    }

    @Test
    @DisplayName(">update")
    void answer_shouldUpdateAnswer() throws Exception {
        //given
        String uri = "/edit/1";
        int id = 1;
        String answerContentBefore = "Answer before";
        String answerContentAfter = "Answer after";
        Answer answerBefore = new Answer(id, answerContentBefore);
        Answer answerAfter = new Answer(id, answerContentAfter);
        String successUpdating = "old answer: " + answerBefore.toString() + "\nreplaced by new answer: " + answerAfter.toString();
        String inputJson = mapToJson(answerAfter);

        //when
//        when(answerService.getAnswerById(id)).thenReturn(answerBefore);
//        when(answerService.update(id, answerBefore)).thenReturn(answerAfter);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        assertEquals(successUpdating, result);
    }

}
