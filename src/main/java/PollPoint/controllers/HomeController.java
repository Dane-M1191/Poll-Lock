package PollPoint.controllers;

import PollPoint.data.AnswerRepository;
import PollPoint.data.CategoryRepository;
import PollPoint.data.PollRepository;
import PollPoint.models.Category;
import PollPoint.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public String displayHome(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userFromSession = authenticationController.getUserFromSession(session);

        //populate category repository
        int count = 0;
        for (Category cat : categoryRepository.findAll()) {
            if (cat != null) count++;
        }
        List<String> categories = Arrays.asList(
            "Food", "Science", "Math", "Memes", "Politics", "Animals", "Nature",
            "Cinema", "Cars", "Hobbies", "Language", "Games", "Cities", "States/Provinces",
            "Countries", "Music", "Celebrities", "Toys", "Sports", "Health", "Climate",
            "Weather", "Tools", "Construnction", "Architecture", "Books"
        );
        if (count == 0){
            int i = 0;
            for (String str : categories) {
                categoryRepository.save(new Category(str));
            }
        }

        model.addAttribute("user", userFromSession);
        model.addAttribute("polls", userFromSession.findTop3(pollRepository.findAllByUserId(userFromSession.getId())));
        model.addAttribute("allPolls", userFromSession.findTop10(pollRepository.findAll()));
        return "index";
    }
}
