package pl.karolskolasinski.magic8ball.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import pl.karolskolasinski.magic8ball.model.AnswerSequence;
import pl.karolskolasinski.magic8ball.model.QuestionSequence;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private final MongoOperations mongoOperations;


    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    public int generateAnswerSequence(String seqName) {
        AnswerSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                AnswerSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }


    public int generateQuestionSequence(String seqName) {
        QuestionSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                QuestionSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
