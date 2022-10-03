package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchAnnouncementRepository extends JpaRepository<BranchAnnouncement, Long> {
    List<BranchAnnouncement> findAllByUser(User u);

    List<BranchAnnouncement> findAllByAnnouncement(Announcement a);
}
