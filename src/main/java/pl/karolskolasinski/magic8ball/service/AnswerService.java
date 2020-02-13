package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.repository.AnswerRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer saveAnswerToDatabase(Answer answer) {
        answerRepository.save(answer);
        return answer;
    }

    public List<Answer> findAllAnswers() {
        return answerRepository.findAll();
    }

    public int countAnswers() {
        return answerRepository.countAnswersByAnswerContentIsNotNull();
    }

    public void update(Answer answer, HttpServletRequest request) {
        Optional<Answer> answerOptional = answerRepository.findById(answer.getId());
        answerOptional.ifPresent(a -> a.setAnswerContent("POPR"));
        answerOptional.ifPresent(answerRepository::save);
    }

    public void deleteAnswer(Long answerId) {
        Optional<Answer> answerOptional = answerRepository.findById(Math.toIntExact(answerId));//todo long -> integer
        answerOptional.ifPresent(answerRepository::delete);
    }
}
