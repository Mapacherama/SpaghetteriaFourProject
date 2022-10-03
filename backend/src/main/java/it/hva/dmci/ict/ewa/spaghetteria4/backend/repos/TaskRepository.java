package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    Task findBy_id(String _id);

    @Query("{'assignees' : ?0}")
    List<Task> findByBranch(String assignee);

    @Query("{'assignees' : ?0, 'submittedBy' : { $nin: [\"?0\"] }}")
    List<Task> findUnfinishedByBranch(String assignee);

    @Query("{'assignees' : ?0, 'deadline' : { $gte: ?1, $lte: ?2 } }")
    List<Task> findAllBetweenTwoDates(String assignee, LocalDate date1, LocalDate date2);

    @Query("{'assignees' : ?0, 'submittedBy' : { $nin: [\"?0\"] }}, 'deadline' : { $lte: ?1 } }")
    List<Task> findAllUnsubmittedForBranchBeforeDate(String assignee, LocalDateTime date);

    @Query("{'assignees' : { $ne: 'submittedBy' }}, 'deadline' : { $lte: ?0 } }")
    List<Task> findAllUnfinishedTasks(LocalDate date);

    @Query("{'name' : ?0, 'deadline' : { $gte: ?1, $lte: ?2 } }")
    Optional<Task> findWithNameAndDate(String name, LocalDate date1, LocalDate date2);


}
