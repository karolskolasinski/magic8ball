package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.repository.QuestionRepository;

@Service
public class AskService {

    private final QuestionRepository questionRepository;

    @Autowired
    public AskService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


}
