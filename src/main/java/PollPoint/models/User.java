package PollPoint.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotNull
    private  String username;

    @NotNull
    private  String pwHash;

    @NotNull
    private String email;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private int points;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Poll> polls = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    public User() { }

    public User(@NotNull String username, @NotNull String password, @NotNull String email, @NotNull String firstName, @NotNull String lastName) {
        this.username = username;
        this.pwHash = encoder.encode(password);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String password) {
        this.pwHash = encoder.encode(password);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Poll> getPolls() {return polls;}
    public void setPolls(List<Poll> polls) {this.polls = polls;}

    public List<Answer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<Answer> answers) { this.answers = answers; }

    public int getPoints() {return points;}
    public void setPoints(int points) {this.points = points;}

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, pwHash);
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + pwHash + '\'' +
                '}';
    }

    public List<Poll> findTop3(List<Poll> userPolls) {
        List<Poll> top3 = new ArrayList<>();

        int answerCount = 0;
        int pollId1 = 0;
        int pollId2 = 0;
        int pollId3 = 0;
        for (Poll poll : userPolls) {
            if (poll != null && poll.getAnswerCount() >= answerCount){
                answerCount = poll.getAnswerCount();
                pollId1 = poll.getId();
            }
        }

        answerCount = 0;
        for (Poll poll : userPolls) {
            if (poll != null && poll.getAnswerCount() >= answerCount && poll.getId() != pollId1){
                answerCount = poll.getAnswerCount();
                pollId2 = poll.getId();
            }
        }

        answerCount = 0;
        for (Poll poll : userPolls) {
            if (poll != null && poll.getAnswerCount() >= answerCount && poll.getId() != pollId1 && poll.getId() != pollId2){
                answerCount = poll.getAnswerCount();
                pollId3 = poll.getId();
            }
        }

        for (Poll poll : userPolls) {
            if (poll != null && poll.getId() == pollId1) {
                top3.add(poll);
            }
        }
        for (Poll poll : userPolls) {
            if (poll != null && poll.getId() == pollId2) {
                top3.add(poll);
            }
        }
        for (Poll poll : userPolls) {
            if (poll != null && poll.getId() == pollId3) {
                top3.add(poll);
            }
        }

        return top3;
    }
}