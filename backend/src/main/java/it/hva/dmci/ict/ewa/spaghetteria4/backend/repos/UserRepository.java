package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for storing users
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    List<User> findAllByEnabled(boolean b);

    List<User> findAllByAuthority(String role);

    List<User> findAllByEnabledAndAuthority(boolean b, String role);

    @Query("SELECT u.enabled FROM User u WHERE u.username = ?1")
    boolean enabledById(String id);
}