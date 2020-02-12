package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.karolskolasinski.magic8ball.service.AskQuestionService;

@Controller
@RequestMapping(path = "/")
public class IndexController {

    private final AskQuestionService askQuestionService;

    @Autowired
    public IndexController(AskQuestionService askQuestionService) {
        this.askQuestionService = askQuestionService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /*Login GET*/
    @GetMapping("/login")
    public String login() {
        return "admin/login-form";
    }

}
