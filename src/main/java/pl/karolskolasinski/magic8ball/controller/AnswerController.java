package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/")
public class AnswerController {

    private AnswerService answerService;
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public AnswerController(AnswerService answerService, SequenceGeneratorService sequenceGeneratorService) {
        this.answerService = answerService;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    /*>>>CRUD<<<*/

    //>create
    @PostMapping("/addAnswer")
    public String createAnswer(Model model, @RequestBody Answer answer) {
        answer.setId(sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME));
        answerService.saveAnswerToDatabase(answer);
        model.addAttribute("savedAnswer", answerService.saveAnswerToDatabase(answer));
        return "panel";
    }

    //>read [all]
    @GetMapping("/getAllAnswers")
    public String getAllAnswers(Model model) {
        List<Answer> allAnswers = answerService.findAllAnswers();
        model.addAttribute("allAnswers", allAnswers);
        return allAnswers.toString();
    }

    //read [one] //todo ▼ countAnswers -> random getOne
    @GetMapping("/countAnswers")
    public String coutntAnswers(Model model) {
        int howMany = answerService.countAnswers();
        return "" + howMany;
    }

    //>update
    @PostMapping("/edit")
    public String updateAnswer(Answer answer, HttpServletRequest request) {
        answerService.update(answer, request);
        return "redirect:" + request.getHeader("referer");
    }

    //>delete
    @GetMapping("/delete/{questionId}")
    public String deleteAnswer(HttpServletRequest request, @PathVariable(name = "questionId") Long answerId) {
        answerService.deleteAnswer(answerId);
        return "redirect:" + request.getHeader("referer");
    }

}
