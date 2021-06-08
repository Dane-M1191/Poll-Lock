package PollPoint.controllers;

import PollPoint.data.AnswerRepository;
import PollPoint.data.PollRepository;
import PollPoint.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    AnswerRepository answerRepository;

    @GetMapping
    public String displayHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute("polls", userFromSession.findTop3(pollRepository.findAllByUserId(userFromSession.getId())));
        model.addAttribute("allPolls", userFromSession.findTop10(pollRepository.findAll()));
        return "index";
    }
}
