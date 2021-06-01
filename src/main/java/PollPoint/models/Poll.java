package PollPoint.models;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Poll extends AbstractEntity {

    private String title;
    private String category;
    private String question;
    private String pollType;
    private String answers;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    private final int POINTS = 5;
    private boolean visibility;

    @ManyToOne
    private User user;

    public Poll() {}

    public Poll(String title, String category, String question, String pollType, String answers, Date startDate, Date endDate, boolean visibility) {
        super();
        this.title = title;
        this.category = category;
        this.question = question;
        this.pollType = pollType;
        this.answers = answers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.visibility = visibility;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPollType() {
        return pollType;
    }
    public void setPollType(String pollType) {
        this.pollType = pollType;
    }

    public String getAnswers() {
        return answers;
    }
    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isVisibility() {
        return visibility;
    }
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
