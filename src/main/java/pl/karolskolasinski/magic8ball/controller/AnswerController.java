package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.karolskolasinski.magic8ball.model.Answer;
import pl.karolskolasinski.magic8ball.service.AnswerService;
import pl.karolskolasinski.magic8ball.service.SequenceGeneratorService;

import java.util.List;

@RestController
//@Controller
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

    //>read
    @GetMapping("/getAllAnswers")
    public String getAllAnswers(Model model) {
        List<Answer> allAnswers = answerService.findAllAnswers();
        model.addAttribute("allAnswers", allAnswers);
        return allAnswers.toString();
    }


//
//    /*Duplicate usernames error*/
//    private String usernamesDuplicateError(Model model, Long newUserQuizId) {
//        model.addAttribute("newUserQuiz", quizSetupService.returnUserQuizById(newUserQuizId));
//        model.addAttribute("errorMessage", "Nie możesz podać dwóch takich samych nazw.");
//        return "quizsetup/quizsetup-usernames";
//    }
//
//    /*Authenticated quiz GET*/
//    @GetMapping("/authQuiz")
//    public String authenticatedQuiz(Model model, Principal principal, UserQuiz newUserQuiz) {
//        quizSetupService.createUserQuizWithGivenNumberOfPlayers((byte) 1, newUserQuiz);
//        newUserQuiz.setAccount(accountService.findByUsername(principal.getName()));
//        quizSetupService.setCategoriesToUserQuizByQuizId(newUserQuiz.getId(), questionService.returnAllCategories());
//        quizSetupService.setUsernamesToUserQuizByQuizId(newUserQuiz.getId(), principal.getName(), null, null, null);
//        model.addAttribute("newUserQuiz", quizSetupService.setCategoriesToUserQuizByQuizId(newUserQuiz.getId(), questionService.returnAllCategories()));
//        return "quizsetup/quizsetup-categories";
//    }

}
