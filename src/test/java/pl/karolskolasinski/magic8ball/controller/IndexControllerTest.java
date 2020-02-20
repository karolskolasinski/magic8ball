package pl.karolskolasinski.magic8ball.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.model.PictureSide;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.repository.AnswerRepository;
import pl.karolskolasinski.magic8ball.repository.QuestionRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionRepository mockQuestionRepository;

    @MockBean
    private AnswerRepository mockAnswerRepository;

    @Test
    void shouldReturnAnswerWhenPostingQuestion() throws Exception {
        //given
        final int ID = 1;
        final String QUESTION_CONTENT = "My question";
        final Timestamp QUESTION_TIMESTAMP = new Timestamp(new Date().getTime());
        final Question QUESTION = new Question(ID, QUESTION_CONTENT, QUESTION_TIMESTAMP);
        final Answer ANSWER = new Answer(1, "Answer");
        final PictureSide FRONT = PictureSide.FRONT;
        final PictureSide BACK = PictureSide.BACK;

        //when
        when(mockQuestionRepository.findById(1)).thenReturn(Optional.of(QUESTION));
        when(mockAnswerRepository.countAnswersByAnswerContentIsNotNull()).thenReturn(1);
        when(mockAnswerRepository.findById(1)).thenReturn(Optional.of(ANSWER));


        //then
        mockMvc.perform(post("/ask"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("answer", ANSWER.getAnswerContent()))
                .andExpect(model().attribute("picture_side", BACK))
                .andExpect(model().attribute("question", QUESTION_CONTENT));
    }

}
