package app.moz.repository;

import app.moz.entity.Clients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Clients, Integer> {


    List<Clients> findByUser_Id(int userId);
}
