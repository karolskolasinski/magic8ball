package pl.karolskolasinski.magic8ball.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Document(collection = "questions")
public class Question {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private int id;

    @NotEmpty
    private Language language;

    @NotEmpty
    @Size(min = 5)
    private String questionContent;

    private Timestamp questionTimestamp;

}
