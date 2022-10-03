package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Contact;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long > {
    @NotNull
    @Override
    Optional<Contact> findById(Long id);

    @Query("SELECT c FROM Contact c WHERE c.id IN(SELECT bc.contact FROM BranchContact bc WHERE bc.user = ?1)")
    List<Contact> findAllForUser(User user);
}
