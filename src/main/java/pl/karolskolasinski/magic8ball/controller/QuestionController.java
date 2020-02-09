package pl.karolskolasinski.magic8ball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.karolskolasinski.magic8ball.repository.QuestionRepository;

@Controller
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping("/create")
    public String create(Model model) {
        return "create";
    }

}
