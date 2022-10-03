package it.hva.dmci.ict.ewa.spaghetteria4.backend.repos;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.RecurringTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringTaskRepository extends MongoRepository<RecurringTask, String> {

    @Query("{'startDate' : { $lte: ?0 }, 'endDate' : { $gte: ?0 } }")
    List<RecurringTask> findAllActive(LocalDate date);
}
