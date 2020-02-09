package pl.karolskolasinski.magic8ball.repository;

import org.springframework.data.repository.CrudRepository;
import pl.karolskolasinski.magic8ball.model.Question;

public interface AnswerRepository extends CrudRepository<Question, Long> {
}
