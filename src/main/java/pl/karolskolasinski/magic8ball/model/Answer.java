package pl.karolskolasinski.magic8ball.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Document(collation = "answers")
public class Answer {

    @Id
    private Long id;

    @NotEmpty
    private Language language;

    @NotEmpty
    private String answerContent;

}
