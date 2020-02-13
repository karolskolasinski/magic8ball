package pl.karolskolasinski.magic8ball.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.karolskolasinski.magic8ball.model.Answer;

public interface AnswerRepository extends MongoRepository<Answer, Integer> {

    int countAnswersByAnswerContentIsNotNull();

}
