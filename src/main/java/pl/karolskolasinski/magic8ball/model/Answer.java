package pl.karolskolasinski.magic8ball.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Document(collection = "answers")
public class Answer {

    @Id
    private int id;

    @NotEmpty
    private Language language;

    @NotEmpty
    private String answerContent;

}
