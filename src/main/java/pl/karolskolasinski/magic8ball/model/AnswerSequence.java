package pl.karolskolasinski.magic8ball.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "answer_sequence")
public class AnswerSequence {

    @Id
    private String id;

    private int seq;

}
