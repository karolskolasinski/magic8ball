package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.model.Question;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.AskQuestionService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import javax.servlet.http.HttpServletRequest;

//@RestController
@Controller
@RequestMapping(path = "/")
public class IndexController {

    private final AskQuestionService askQuestionService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final AnswerService answerService;

    @Autowired
    public IndexController(AskQuestionService askQuestionService, SequenceGeneratorService sequenceGeneratorService, AnswerService answerService) {
        this.askQuestionService = askQuestionService;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.answerService = answerService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/ask")
    public String askQuestion(Model model, @RequestBody Question question, HttpServletRequest request) {
        askQuestionService.saveQuestionToDatabase(question, sequenceGeneratorService, request);
        Answer randomAnswer = answerService.getRandomAnswer();
        model.addAttribute("answer", randomAnswer);
        return "answer: " + randomAnswer.toString();
    }


    @GetMapping("/login")
    public String login() {
        return "admin/login-form";
    }

}
