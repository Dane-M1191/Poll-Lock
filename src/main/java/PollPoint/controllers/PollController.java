package PollPoint.controllers;

import PollPoint.models.Poll;
import PollPoint.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("poll")
public class PollController {

    Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private AuthenticationController authenticationController;

    @GetMapping("create")
    public String displayCreatePollForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute(new Poll());
        return "create";
    }
}