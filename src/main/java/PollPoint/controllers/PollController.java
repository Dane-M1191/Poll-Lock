package PollPoint.controllers;

import PollPoint.data.AnswerRepository;
import PollPoint.data.PollRepository;
import PollPoint.models.Answer;
import PollPoint.models.Poll;
import PollPoint.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("poll")
public class PollController {

//    Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("create")
    public String displayCreatePollForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute(new Poll());
        return "poll/create";
    }

    @PostMapping("create")
    public String processCreatePollForm(@ModelAttribute @Valid Poll newPoll, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        newPoll.setUser(userFromSession);
        pollRepository.save(newPoll);
        userFromSession.getPolls().add(newPoll);
        model.addAttribute("user", userFromSession);
        return "redirect:../";
    }

    @GetMapping("answer/{pollId}")
    public String displayPollAnswerForm(@PathVariable int pollId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute("poll", pollRepository.findById(pollId).get());
        model.addAttribute(new Answer());
        return "poll/answer";
    }

    @PostMapping("answer/{pollId}")
    public String processPollAnswerForm(@PathVariable int pollId, @ModelAttribute @Valid Answer newAnswer, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        Poll poll = pollRepository.findById(pollId).get();
        newAnswer.setUser(userFromSession);
        newAnswer.setPoll(poll);
        poll.getAnswers().add(newAnswer);
         int answerCount = poll.getAnswerCount();
        poll.setAnswerCount(++answerCount);
        pollRepository.save(poll);
        answerRepository.save(newAnswer);

        return "redirect:../../";
    }

    @GetMapping("list/{userId}")
    public String displayUserPollList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute("polls", pollRepository.findAllByUserId(userFromSession.getId()));
        return "poll/list";
    }

    @GetMapping("list")
    public String displayAllPollList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute("polls", pollRepository.findAll());
        return "poll/listall";
    }
}