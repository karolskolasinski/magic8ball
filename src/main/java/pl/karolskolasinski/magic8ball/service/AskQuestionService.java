package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.repository.AskQuestionRepository;

@Service
public class AskQuestionService {

    private final AskQuestionRepository askQuestionRepository;

    @Autowired
    public AskQuestionService(AskQuestionRepository askQuestionRepository) {
        this.askQuestionRepository = askQuestionRepository;
    }

    public void saveQuestionToDatabase(Question question) {
        askQuestionRepository.save(question);
    }
}
