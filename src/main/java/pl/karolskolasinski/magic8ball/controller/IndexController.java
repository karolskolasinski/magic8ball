package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.service.AskQuestionService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    private final AskQuestionService askQuestionService;
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public IndexController(AskQuestionService askQuestionService, SequenceGeneratorService sequenceGeneratorService) {
        this.askQuestionService = askQuestionService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/ask")
    public String askQuestion(@RequestBody Question question) {
        question.setId(sequenceGeneratorService.generateQuestionSequence(Question.SEQUENCE_NAME));
        question.setQuestionTimestamp(new Timestamp(new Date().getTime()));
        askQuestionService.saveQuestionToDatabase(question);
        return "";
    }


    /*Login GET*/
    @GetMapping("/login")
    public String login() {
        return "admin/login-form";
    }

}
