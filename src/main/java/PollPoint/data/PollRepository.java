package PollPoint.data;

import PollPoint.models.Poll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollRepository extends CrudRepository<Poll, Integer> {
    List<Poll> findAllByUserId(int UserId);
    List<Poll> findAll();
}
