package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.karolskolasinski.magic8ball.service.AskService;

import java.text.DecimalFormat;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    private final AskService askService;
    private DecimalFormat decimalFormat = new DecimalFormat("##.##");

    @Autowired
    public IndexController(AskService askService) {
        this.askService = askService;
    }

    /*Dispaly index with statistisc GET*/
    @GetMapping("/")
    public String index(Model model) {
        /*Last month statistics*/
        model.addAttribute("gamesPlayedAll", quizSetupService.gamesPlayedAll());
        model.addAttribute("percentageOfCorrectAnswersAll", decimalFormat.format(quizSetupService.correctAnswersAll() * 100 / quizSetupService.allAnswersAll()));
        model.addAttribute("bestScoreAll", quizSetupService.bestScoreAll());

        return "index";
    }

    /*Login GET*/
    @GetMapping("/login")
    public String login() {
        return "account/login-form";
    }

    /*Start new quiz GET*/
    @GetMapping("quizSetup/numberofplayers")
    public String play() {
        return "quizsetup/quizsetup-numberofplayers";
    }
}
