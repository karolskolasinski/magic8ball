package pl.karolskolasinski.magic8ball.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "answers")
public class Answer {

    @Transient
    public static final String SEQUENCE_NAME = "sequence";


    @Id
    private int id;


    @NotEmpty
    @Indexed(unique = true)
    private String answerContent;

}
