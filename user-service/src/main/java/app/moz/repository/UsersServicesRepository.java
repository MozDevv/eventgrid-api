package app.moz.repository;

import app.moz.entity.User;
import app.moz.entity.UserServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersServicesRepository extends JpaRepository<UserServices, Integer> {

    //

    List<UserServices> findByUser(User user);

}
