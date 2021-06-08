package PollPoint.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
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

        userPolls.sort(Comparator.comparingInt(Poll::getAnswerCount).reversed());
        int count = 0;
        for (Poll poll : userPolls) {
            if (count == 3) {
                break;
            }
            top3.add(userPolls.get(count));
            count++;
        }
        return top3;
    }

    public List<Poll> findTop10(List<Poll> userPolls) {
        List<Poll> top10 = new ArrayList<>();

        userPolls.sort(Comparator.comparingInt(Poll::getAnswerCount).reversed());
        int count = 0;
        for (Poll poll : userPolls) {
            if (count == 10) {
                break;
            }
            top10.add(userPolls.get(count));
            count++;
        }
        return top10;
    }
}