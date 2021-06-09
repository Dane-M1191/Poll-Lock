package PollPoint.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Poll extends AbstractEntity {

    private String title;
    private String question;
    private String pollType;

    @ManyToMany
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "poll", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;
    private final int POINTS = 5;
    private boolean visibility;
    private int answerCount;

    @ManyToOne
    private User user;

    public Poll() {}

    public Poll(String title, List<Category> categories, String question, String pollType, Date startDate, Date endDate, boolean visibility) {
        super();
        this.title = title;
        this.categories = categories;
        this.question = question;
        this.pollType = pollType;
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

    public List<Category> getCategories() {return categories;}
    public void setCategories(List<Category> categories) {this.categories = categories;}

    public List<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<Answer> answers) { this.answers = answers; }

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

    public int getPOINTS() { return POINTS; }

    public int getAnswerCount() {return answerCount;}
    public void setAnswerCount(int answerCount) {this.answerCount = answerCount;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}
