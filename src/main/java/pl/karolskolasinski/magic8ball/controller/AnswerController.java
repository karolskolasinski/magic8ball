package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;


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

    /*CRUD ▼*/

    //>create
    @PostMapping("/addAnswer")
    public String createAnswer(@RequestBody Answer answer) {
        answer.setId(sequenceGeneratorService.generateAnswerSequence(Answer.SEQUENCE_NAME));
        answerService.saveAnswerToDatabase(answer);
        return "success adding: " + answer.toString();
    }

    //>read [all]
    @GetMapping("/getAllAnswers")
    public String getAllAnswers() {
        return answerService.findAllAnswers().toString();
    }

    //>update
    @PostMapping("/edit/{answerToEditId}")
    public String updateAnswer(@PathVariable int answerToEditId, @RequestBody Answer answer) {
        Answer before = answerService.getAnswerById(answerToEditId);
        Answer after = answerService.update(answerToEditId, answer);
        return "old answer: " + before.toString() + "\nreplaced by new answer: " + after.toString();
    }

    //>delete
    @PostMapping("/delete/{questionId}")
    public String deleteAnswer(@PathVariable(name = "questionId") int answerId) {
        answerService.deleteAnswer(answerId);
        return "success deleting answer id: " + answerId;
    }

}
