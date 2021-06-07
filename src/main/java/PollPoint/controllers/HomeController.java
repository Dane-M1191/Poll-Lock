package PollPoint.controllers;

import PollPoint.data.PollRepository;
import PollPoint.models.Poll;
import PollPoint.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    PollRepository pollRepository;

    @GetMapping
    public String displayHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
//        model.addAttribute("polls", pollRepository.findAllByUserId(userFromSession.getId()));
        model.addAttribute("polls", userFromSession.findTop3(pollRepository.findAllByUserId(userFromSession.getId())));
        model.addAttribute("allPolls", pollRepository.findAll());

        return "index";
    }
}
