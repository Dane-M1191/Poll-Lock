package PollPoint.controllers;

import PollPoint.data.AnswerRepository;
import PollPoint.data.CategoryRepository;
import PollPoint.data.PollRepository;
import PollPoint.data.UserRepository;
import PollPoint.models.Answer;
import PollPoint.models.Category;
import PollPoint.models.Poll;
import PollPoint.models.User;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("create")
    public String displayCreatePollForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);

        //create blank category
        if (categoryRepository.findByCategoryString("") == null) {
            Category blankCategory = new Category("");
            categoryRepository.save(blankCategory);
        }


        model.addAttribute("user", userFromSession);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute(new Poll());
        return "poll/create";
    }

    @PostMapping("create")
    public String processCreatePollForm(@ModelAttribute @Valid Poll newPoll, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        //create poll
        newPoll.setUser(userFromSession);
        pollRepository.save(newPoll);
        //assign points
        int userPts = userFromSession.getPoints();
        userFromSession.setPoints(userPts + newPoll.getPOINTS());
        userRepository.save(userFromSession);
        return "redirect:../";
    }

    @GetMapping("answer/{pollId}")
    public String displayPollAnswerForm(@PathVariable int pollId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);

        Poll poll = pollRepository.findById(pollId).get();

        //If user has already answered, edit answer
        Answer userAnswer = null;
        for (Answer answer : poll.getAnswers()) {
            if (answer != null && answer.getUser().getId().equals(userFromSession.getId())) {
                userAnswer = answer;
            }
        }
        if (userAnswer != null) {
            return "redirect:edit/" + poll.getId() + "/" + userAnswer.getId();
        }
        //------------------------------
        model.addAttribute("user", userFromSession);
        model.addAttribute("poll", poll);
        model.addAttribute(new Answer());
        return "poll/answer/answer";
    }

    @PostMapping("answer/{pollId}")
    public String processPollAnswerForm(@PathVariable int pollId, @ModelAttribute @Valid Answer newAnswer, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);

        //create answer
        Poll poll = pollRepository.findById(pollId).get();
        newAnswer.setUser(userFromSession);
        newAnswer.setPoll(poll);
         int answerCount = poll.getAnswerCount();
        poll.setAnswerCount(++answerCount);
        pollRepository.save(poll);
        answerRepository.save(newAnswer);

        //assign points to user for answering
        int userPts = userFromSession.getPoints();
        userFromSession.setPoints(userPts + 3);
        userRepository.save(userFromSession);

        //assign points to poll owner
        User pollOwner = userRepository.findById(poll.getUser().getId()).get();
        int pollOwnerPts = pollOwner.getPoints();
        pollOwner.setPoints(pollOwnerPts + 2);
        userRepository.save(pollOwner);

        return "redirect:../../";
    }

    @GetMapping("answer/edit/{pollId}/{answerId}")
    public String displayEditAnswerForm(@PathVariable int pollId, @PathVariable int answerId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        model.addAttribute("user", userFromSession);
        model.addAttribute("answer", answerRepository.findById(answerId));
        model.addAttribute("poll", pollRepository.findById(pollId).get());
        return "poll/answer/edit";
    }

    @PostMapping("answer/edit/{pollId}/{answerId}")
    public String processEditAnswerForm(@ModelAttribute @Valid Answer answer, @PathVariable int pollId, @PathVariable int answerId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);
        //get answer
        Answer userAnswer = answerRepository.findById(answerId).get();
        //update users answer
        userAnswer.setAnswerString(answer.getAnswerString());
        answerRepository.save(userAnswer);
        return "redirect:/";
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