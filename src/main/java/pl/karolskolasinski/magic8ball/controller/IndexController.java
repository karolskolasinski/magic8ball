package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.karolskolasinski.magic8ball.model.PictureSide;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.AskQuestionService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

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
    public String index(Model model) {
        model.addAttribute("picture_side", PictureSide.FRONT);
        return "index";
    }

    @PostMapping("/ask")
    public String askQuestion(Model model, @ModelAttribute("question_input") String question_input) {
        askQuestionService.saveQuestionToDatabase(sequenceGeneratorService, question_input);
        model.addAttribute("answer", answerService.getRandomAnswer().getAnswerContent());
        model.addAttribute("picture_side", PictureSide.BACK);
        return "index";
    }

}
