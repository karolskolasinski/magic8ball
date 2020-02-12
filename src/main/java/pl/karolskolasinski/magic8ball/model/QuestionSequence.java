package pl.karolskolasinski.magic8ball.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "question_sequence")
public class QuestionSequence {

    @Id
    private String id;

    private int seq;

}
