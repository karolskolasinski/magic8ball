package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.repository.QuestionRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    //todo @Size
    public void saveQuestionToDatabase(SequenceGeneratorService sequenceGeneratorService, @NotEmpty @Size(min = 5) String questionContent) {
        Question question = new Question();
        question.setId(sequenceGeneratorService.generateQuestionSequence(Question.SEQUENCE_NAME));
        question.setQuestionTimestamp(new Timestamp(new Date().getTime()));
        question.setQuestionContent(questionContent);
        questionRepository.save(question);
    }
}
