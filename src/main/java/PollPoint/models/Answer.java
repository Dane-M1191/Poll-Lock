package PollPoint.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractEntity{

    private String answerString;

    @ManyToOne
    private User user;

    @ManyToOne
    private Poll poll;

    public Answer(){}

    public Answer(String answerString, User user, Poll poll){
        super();
        this.answerString = answerString;
        this.user = user;
        this.poll = poll;
    }

    public String getAnswerString() {return answerString;}
    public void setAnswerString(String answerString) {this.answerString = answerString;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Poll getPoll() {return poll;}
    public void setPoll(Poll poll) {this.poll = poll;}
}
