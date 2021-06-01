package PollPoint.data;

import PollPoint.models.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Integer> {
}
