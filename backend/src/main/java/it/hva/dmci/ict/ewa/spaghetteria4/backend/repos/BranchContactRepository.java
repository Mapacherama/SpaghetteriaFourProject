package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.BranchContact;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Contact;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchContactRepository extends JpaRepository<BranchContact, Long> {
    List<BranchContact> findAllByUser(User u);

    List<BranchContact> findAllByContact(Contact c);
}
