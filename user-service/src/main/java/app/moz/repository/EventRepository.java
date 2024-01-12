package app.moz.repository;

import app.moz.entity.Event;
import app.moz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByUser(User user);

}
