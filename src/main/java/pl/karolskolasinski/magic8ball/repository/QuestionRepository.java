package pl.karolskolasinski.magic8ball.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.karolskolasinski.magic8ball.model.Question;

public interface QuestionRepository extends MongoRepository<Question, Integer> {
}
