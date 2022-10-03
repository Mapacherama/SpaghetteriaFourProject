package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Announcement;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Task;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @NotNull
    @Override
    Optional<Announcement> findById(Long id);

    @Query("SELECT a FROM Announcement a WHERE a.id IN(SELECT ba.announcement FROM BranchAnnouncement ba WHERE ba.user = ?1)")
    List<Announcement> findAllForUser(User user);

    @Query("SELECT a FROM Announcement a WHERE a.id IN(SELECT ba.announcement FROM BranchAnnouncement ba WHERE ba.user = ?1) AND a.startDate >= ?2 AND a.endDate <= ?3")
    List<Announcement> findAllBetweenTwoDates(User assignee, LocalDateTime date1, LocalDateTime date2);
}
