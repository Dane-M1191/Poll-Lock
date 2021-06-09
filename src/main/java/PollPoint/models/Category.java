package PollPoint.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity{

    @NotBlank(message = "Please enter a category name.")
    private String categoryString;

    @ManyToMany(mappedBy = "categories")
    private List<Poll> polls = new ArrayList<>();

    public Category () {

    }

    public Category (String categoryString) {
        super();
        this.categoryString = categoryString;
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }
}
