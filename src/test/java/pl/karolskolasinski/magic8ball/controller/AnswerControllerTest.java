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
import org.springframework.http.HttpStatus;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AnswerController CRUD ▼ Test")
class AnswerControllerTest {

    private static final int UNKNOWN_ID = Integer.MAX_VALUE;

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
    @DisplayName("should createAnswer with status OK")
    void answer_shouldSaveAnswerToDBWithStatusOk() throws Exception {
        //given
        String uri = "/admin/addAnswer";
        String sequenceName = Answer.SEQUENCE_NAME;

        int id = sequenceGeneratorService.generateAnswerSequence(sequenceName);
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
                .andExpect(content().string(successAdding))
                .andDo(print())
                .andReturn();

        verify(sequenceGeneratorService, times(2)).generateAnswerSequence(sequenceName);
        verify(answerService, times(1)).saveAnswerToDatabase(answer);
        verifyNoMoreInteractions(answerService);

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(successAdding, result);
    }


    @Test
    @DisplayName("should getAllAnswers in toString format")
    void answer_shouldGetAllAnswersInToStringFormat() throws Exception {
        //given
        String uri = "/admin//getAllAnswers";

        int answer1Id = sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME);
        int answer2Id = sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME);
        String answer1Content = "Answer1";
        String answer2Content = "Answer2";

        Answer answer1 = new Answer(answer1Id, answer1Content);
        Answer answer2 = new Answer(answer2Id, answer2Content);

        List<Answer> answerList = new ArrayList<>();
        answerList.add(answer1);
        answerList.add(answer2);

        given(answerService.findAllAnswers()).willReturn(answerList);

        //then
        MockHttpServletResponse response = mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        verify(answerService, times(1)).findAllAnswers();
        verifyNoMoreInteractions(answerService);

        String result = response.getContentAsString();

        assertNotNull(result);
        assertEquals(answerList.toString(), result);
    }


    @Test
    @DisplayName("should UpdateAnswer with status OK")
    void answer_shouldUpdateAnswerWithStatusOk() throws Exception {
        //given
        String uri = "/admin/edit/{answerToEditId}";

        int answerId = 1;
        String answerContentBefore = "Answer before";
        String answerContentAfter = "Answer after";

        Answer answerBefore = new Answer(answerId, answerContentBefore);
        Answer answerAfter = new Answer(answerId, answerContentAfter);

        String successUpdating = "old answer: " + answerBefore.toString() + "\nreplaced by new answer: " + answerAfter.toString();
        String inputJson = mapToJson(answerAfter);

        given(answerService.getAnswerByIdBeforeUpdating(answerId)).willReturn(answerBefore);
        given(answerService.update(answerId, answerAfter)).willReturn(answerAfter);

        //then
        MvcResult mvcResult = mockMvc.perform(put(uri, answerId)
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(successUpdating))
                .andDo(print())
                .andReturn();

        verify(answerService, times(1)).getAnswerByIdBeforeUpdating(answerId);
        verify(answerService, times(1)).update(answerId, answerAfter);
        verifyNoMoreInteractions(answerService);

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(successUpdating, result);
    }


    @Test
    @DisplayName("should deleteAnswer with status OK")
    void answer_shouldDeleteAnswerWithStatusOk() throws Exception {
        //given
        String uri = "/admin/delete/{answerId}";

        int answerId = 1;

        given(answerService.deleteAnswer(answerId)).willReturn(HttpStatus.OK);

        String successDeleting = "success deleting answer id: " + answerId;

        //then
        MvcResult mvcResult = mockMvc.perform(delete(uri, answerId))
                .andExpect(status().isOk())
                .andExpect(content().string(successDeleting))
                .andDo(print())
                .andReturn();

        verify(answerService, times(1)).deleteAnswer(answerId);
        verifyNoMoreInteractions(answerService);

        String result = mvcResult.getResponse().getContentAsString();

        assertNotNull(result);
        assertEquals(successDeleting, result);
    }


    @Test
    @DisplayName("should fail deleteAnswer with status 404 Not Found")
    void answer_shouldFailDeleteAnswerWithStatus404NotFound() throws Exception {
        //given
        String uri = "/admin/delete/{answerId}";

        given(answerService.deleteAnswer(UNKNOWN_ID)).willReturn(HttpStatus.NOT_FOUND);

        //then
        mockMvc.perform(delete(uri, UNKNOWN_ID))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        verify(answerService, times(1)).deleteAnswer(UNKNOWN_ID);
        verifyNoMoreInteractions(answerService);
    }

}
