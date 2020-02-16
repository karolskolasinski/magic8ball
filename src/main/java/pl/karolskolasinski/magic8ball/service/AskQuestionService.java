package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.repository.AskQuestionRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AskQuestionService {

    private final AskQuestionRepository askQuestionRepository;

    @Autowired
    public AskQuestionService(AskQuestionRepository askQuestionRepository) {
        this.askQuestionRepository = askQuestionRepository;
    }

    public void saveQuestionToDatabase(SequenceGeneratorService sequenceGeneratorService, @NotEmpty @Size(min = 5) String questionContent) {
        Question question = new Question();
        question.setId(sequenceGeneratorService.generateQuestionSequence(Question.SEQUENCE_NAME));
        question.setQuestionTimestamp(new Timestamp(new Date().getTime()));
        question.setQuestionContent(questionContent);
        askQuestionRepository.save(question);
    }
}
