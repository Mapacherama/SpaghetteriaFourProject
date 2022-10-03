package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Task;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.TaskSubmission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskSubmissionRepository extends MongoRepository<TaskSubmission, String> {

    TaskSubmission findBy_id(String _id);

    @Query("{'task' : ?0, 'branch' : ?1}")
    TaskSubmission findByTaskAndBranch(String taskId, String branchName);
}
