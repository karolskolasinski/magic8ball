package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.repository.AnswerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public void saveAnswerToDatabase(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> findAllAnswers() {
        return answerRepository.findAll();
    }

    private int countAnswers() {
        return answerRepository.countAnswersByAnswerContentIsNotNull();
    }

    public Answer update(int answerToEditId, Answer answer) {
        Optional<Answer> answerOptional = answerRepository.findById(answerToEditId);
        answerOptional.ifPresent(a -> a.setAnswerContent(answer.getAnswerContent()));
        answerOptional.ifPresent(answerRepository::save);
        return answerOptional.orElseGet(() -> noAnswer("No answer to update"));
    }

    public void deleteAnswer(int answerId) {
        Optional<Answer> answerOptional = answerRepository.findById(answerId);
        answerOptional.ifPresent(answerRepository::delete);
    }

    public Answer getRandomAnswer() {
        Random random = new Random();
        int i = random.nextInt(countAnswers()) + 1;

        Optional<Answer> answerOptional = answerRepository.findById(i);
        return answerOptional.orElseGet(() -> noAnswer("I have no answer for that"));
    }

    private Answer noAnswer(String s) {
        Answer answer = new Answer();
        answer.setAnswerContent(s);
        return answer;
    }

}
