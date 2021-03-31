package hr.tvz.susac.java.web.flight.repository;

import hr.tvz.susac.java.web.flight.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByUsername(String username);

    User save(User user);
}
