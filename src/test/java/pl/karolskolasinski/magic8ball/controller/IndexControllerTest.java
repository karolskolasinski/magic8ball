package pl.karolskolasinski.magic8ball.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.model.PictureSide;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.QuestionService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import java.sql.Timestamp;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IndexControllerTest {

    @Mock
    QuestionService questionService;

    @Mock
    SequenceGeneratorService sequenceGeneratorService;

    @Mock
    AnswerService answerService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        IndexController indexController = new IndexController(questionService, sequenceGeneratorService, answerService);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    void index_shouldReturnStatusOkAndIndexAsViewName() throws Exception {
        PictureSide FRONT = PictureSide.FRONT;

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("question"))
                .andExpect(model().attribute("picture_side", FRONT))
                .andExpect(model().attribute("question", ""));
    }

    @Test
    void index_shouldReturnAnswerWhenPostingQuestion() throws Exception {
        //given
        int answer_id = 1;
        String answer_content = "Answer";
        String question_content = "My question";
        Answer answer = new Answer(answer_id, answer_content);
        PictureSide back = PictureSide.BACK;

        //when
        when(answerService.getRandomAnswer()).thenReturn(answer);

        //then
        mockMvc.perform(post("/ask")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .flashAttr("question", question_content))
                .andExpect(model().attribute("picture_side", back))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("answer", answer.getAnswerContent()));
    }

}
