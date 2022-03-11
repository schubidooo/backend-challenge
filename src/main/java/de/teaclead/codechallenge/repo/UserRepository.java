package de.teaclead.codechallenge.repo;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import de.teaclead.codechallenge.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  @Query("SELECT u FROM users u WHERE u.firstname = :firstname ")
  List<User> findByFirstName(@Param("firstname") String firstname);
}
