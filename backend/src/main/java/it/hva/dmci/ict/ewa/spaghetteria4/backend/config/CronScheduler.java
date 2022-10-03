package it.hva.dmci.ict.ewa.spaghetteria4.backend.config;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.enums.RecurringOptions;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.RecurringTask;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Task;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.RecurringTaskRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.TaskRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.*;

@Component
public class CronScheduler {
    private final Log logger = LogFactory.getLog(getClass());
    private final RecurringTaskRepository recurringTaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CronScheduler(RecurringTaskRepository recurringTaskRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.recurringTaskRepository = recurringTaskRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 7 * * *", zone = "Europe/Amsterdam")
    public void generateRecurringTasks() {
        Runnable task = () -> {
            logger.info("Generating recurring tasks");

            List<RecurringTask> tasks = recurringTaskRepository.findAllActive(LocalDate.now());

            for (RecurringTask rt : tasks) {
                LocalDate currentDate = LocalDate.now();
                Period p = Period.between(rt.getStartDate().toLocalDate().minusDays(1), currentDate);
                int daysBetween = p.getDays()-1;

//                printDetails(rt,p);

                switch (rt.getIntervalUnit()) {
                    case DAYS -> {
                        if (daysBetween % (double) rt.getIntervalAmount() != 0.0)
                            continue; // Check if X days after start
                    }
                    case WEEKS -> {
                        if (rt.getStartDate().getDayOfWeek() != currentDate.getDayOfWeek())
                            continue; // Check if same day of week
                        if ((daysBetween / 7.0) % (double) rt.getIntervalAmount() != 0.0)
                            continue;  // Check if X weeks after start
                    }
                    case MONTHS -> {
                        if (rt.getStartDate().getDayOfMonth() != currentDate.getDayOfMonth())
                            continue; // Check if same day of month
                        if (p.getMonths() % (double) rt.getIntervalAmount() != 0.0)
                            continue;          // Check if X months after start
                    }
                }
//                System.out.println("saving");

                Date deadline = new Date();
                deadline.setTime(rt.getStartDate().toLocalTime().toEpochSecond(LocalDate.now(),ZoneOffset.UTC) * 1000);

                rt.getAssignees().removeIf(this::isDisabled); // Remove disabled branches

                taskRepository.save(Task.fromRecurringTask(rt, deadline));
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    private boolean isDisabled(String username) {
        return !userRepository.existsById(username) || !userRepository.enabledById(username);
    }

    private void printDetails(RecurringTask rt, Period p) {
        System.out.println("----------------");
        System.out.println(LocalDateTime.now());
        System.out.println("Name: " + rt.getName());
        System.out.println("Startdate: " + rt.getStartDate());
        System.out.println("Enddate: " + rt.getEndDate());
        System.out.println("Unit: " + rt.getIntervalUnit());
        System.out.println("Amount: " + rt.getIntervalAmount());
        System.out.print("Days between: " + (p.getDays()-1) + " | ");
        System.out.println((p.getDays()-1) % (double) rt.getIntervalAmount() == 0.0);
        System.out.print("Weeks between: " + (p.getDays()-1)/7.0 + " | ");
        System.out.println(((p.getDays()-1) / 7.0) % (double) rt.getIntervalAmount() == 0.0);
        System.out.println("tt " + rt.getStartDate().getDayOfWeek() + " " + LocalDate.now().getDayOfWeek());
        System.out.println("test " + 7/7.0 + " | " + (7/7.0)%1.0);
    }
}
