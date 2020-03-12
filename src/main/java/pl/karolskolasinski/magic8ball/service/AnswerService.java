package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private Optional<Answer> findOneById(int id) {
        return answerRepository.findById(id);
    }

    private int countAnswers() {
        return answerRepository.countAnswersByAnswerContentIsNotNull();
    }

    public Answer getAnswerById(int id) {
        Optional<Answer> answerOptional = findOneById(id);
        return answerOptional.orElseGet(() -> noAnswer("Answer not found."));
    }

    public Answer getAnswerByIdBeforeUpdating(int answerToEditId) {
        Answer answerById = getAnswerById(answerToEditId);
        return new Answer(answerById.getId(), answerById.getAnswerContent());
    }

    public Answer update(int answerToEditId, Answer answerForUpdate) {
        Answer answerById = getAnswerById(answerToEditId);
        answerById.setAnswerContent(answerForUpdate.getAnswerContent());
        answerRepository.save(answerById);
        return answerById;
    }

    public HttpStatus deleteAnswer(int answerId) {
        Optional<Answer> answerOptional = findOneById(answerId);
        if (answerOptional.isPresent()) {
            answerOptional.ifPresent(answerRepository::delete);
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public Answer getRandomAnswer() {
        Random random = new Random();
        int i = random.nextInt(countAnswers()) + 1;
        Optional<Answer> answerOptional = findOneById(i);
        return answerOptional.orElseGet(() -> noAnswer("I have no answer for that."));
    }

    private Answer noAnswer(String answerContent) {
        Answer answer = new Answer();
        answer.setAnswerContent(answerContent);
        return answer;
    }
}
