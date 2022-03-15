package de.teaclead.codechallenge.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import de.teaclead.codechallenge.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u FROM users u WHERE u.firstname = :firstname ")
  List<User> findByFirstname(@Param("firstname") String firstname);
}
