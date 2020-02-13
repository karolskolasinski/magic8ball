package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.repository.AnswerRepository;
import pl.karolskolasinski.magic8ball.repository.AskQuestionRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
public class AskQuestionService {

    private final AskQuestionRepository askQuestionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public AskQuestionService(AskQuestionRepository askQuestionRepository, AnswerRepository answerRepository) {
        this.askQuestionRepository = askQuestionRepository;
        this.answerRepository = answerRepository;
    }

    public void saveQuestionToDatabase(Question question, SequenceGeneratorService sequenceGeneratorService, HttpServletRequest request) {
        question.setId(sequenceGeneratorService.generateQuestionSequence(Question.SEQUENCE_NAME));
        question.setQuestionTimestamp(new Timestamp(new Date().getTime()));

        //todo ▼ method set questionContent + language
        Map<String, String[]> parameterMap = request.getParameterMap();


        askQuestionRepository.save(question);
    }
}
