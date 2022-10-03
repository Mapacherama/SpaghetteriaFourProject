package it.hva.dmci.ict.ewa.spaghetteria4.backend.services;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Task;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.TaskDto;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.TaskSubmission;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.RecurringTaskRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.TaskRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.TaskSubmissionRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskSubmissionRepository taskSubmissionRepository;
    private final RecurringTaskRepository recurringTaskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, TaskSubmissionRepository taskSubmissionRepository, RecurringTaskRepository recurringTaskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskSubmissionRepository = taskSubmissionRepository;
        this.recurringTaskRepository = recurringTaskRepository;
        this.userRepository = userRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public TaskSubmissionRepository getTaskSubmissionRepository() {
        return taskSubmissionRepository;
    }

    public RecurringTaskRepository getRecurringTaskRepository() { return  recurringTaskRepository; }

    /*
        Task related
     */

    public Task save(Task t) {
        return taskRepository.save(t);
    }

    /**
     * Returns all unfinished tasks for a branch sorted by deadline
     *
     * @param branch - branch for which to get tasks
     * @return List of Tasks for branch
     */
    public List<Task> findUnfinishedByAssignee(String branch) {
        List<Task> tasks = taskRepository.findUnfinishedByBranch(branch);
        tasks.sort(TaskService::compareToDate);
        return tasks;
    }

    /**
     * Returns all tasks for a branch sorted by deadline
     *
     * @param branch - branch for which to get tasks
     * @return List of Tasks for branch
     */
    public List<Task> findByAssignee(String branch) {
        List<Task> tasks = taskRepository.findByBranch(branch);
        tasks.sort(TaskService::compareToDate);
        return tasks;
    }

    /**
     * Save a TaskDto as Task
     *
     * @param t - TaskDto
     * @return Dto as Task
     */
    public Task saveDto(TaskDto t) {
        return save(new Task(t.getName(), t.getDescription(), t.getDeadline(), t.getAssignees(), t.getQuestions(), t.getSubmittedBy()));
    }

    /**
     * Add branch to list of branches that have submitted their task for review
     *
     * @param taskId - the task
     * @param name - the branch
     * @return Returns the task if succesful, otherwise returns null
     */
    public Task submit(String taskId, String name) {
        Task t = this.taskRepository.findBy_id(taskId);
        if (t.addSubmitted(name)) return save(t);
        return null;
    }

    /**
     * Removes branch from list of branches that have submitted their task for review
     *
     * @param taskId - the task
     * @param name - the branch
     * @return Returns the task if succesful, otherwise returns null
     */
    public Task unsubmit(String taskId, String name) {
        Task t = this.taskRepository.findBy_id(taskId);
        if (t.removeSubmitted(name)) return save(t);
        return null;
    }

    /**
     *
     * @param branch - which branch to search for
     * @param start - Inner boundary (inclusive)
     * @param end - Outer boundary (inclusive)
     * @return All tasks fitting criteria
     */
    public List<Task> getForBranchBetweenDates(String branch, LocalDate start, LocalDate end) {
        end = end.plusDays(1);
        List<Task> tasks = this.taskRepository.findAllBetweenTwoDates(branch,start,end);
        tasks.sort(TaskService::compareToDate);
        return tasks;
    }

    /**
     *
     * @param date
     * @return
     */
    public Task getReportsForDate(LocalDate date) {
        final String name = "Logboek";
        LocalDate end = date.plusDays(1);
        Optional<Task> task = this.taskRepository.findWithNameAndDate(name,date,end);
        return task.orElse(null);
    }

    /*
        TaskSubmission related
     */

    public TaskSubmission save(TaskSubmission t) {
        return taskSubmissionRepository.save(t);
    }

    /**
     *
     * @param taskId - ID of task
     * @param name - Name of branch
     * @return either existing submission, or of none is found new one is made and returned.
     */
    public TaskSubmission getSubmission(String taskId, String name) {
        TaskSubmission taskSubmission = taskSubmissionRepository.findByTaskAndBranch(taskId, name);
        if (taskSubmission == null) {
            taskSubmission = new TaskSubmission(taskId,name, new String[this.taskRepository.findBy_id(taskId).getQuestionsLength()], LocalDateTime.now());
            taskSubmissionRepository.save(taskSubmission);
        }
        return taskSubmission;
    }

    public List<Map<String, Object>> getPastDueTasks(LocalDate dateTime) {
        List<Map<String, Object>> tasks = new ArrayList<>();
        List<Task> tasksList = taskRepository.findAllUnfinishedTasks(dateTime);
        tasksList.sort(TaskService::compareToDate);
        for (Task task : tasksList) {
            Map<String, Object> map = new HashMap<>();

            map.put("id", task.get_id());
            map.put("name", task.getName());
            map.put("deadline", task.getDeadline());
            map.put("submitted", task.getSubmittedBy());

            List<Object> notSubmitted = new ArrayList<>(task.getAssignees());
            notSubmitted.removeAll(task.getSubmittedBy());
            if (notSubmitted.isEmpty()) continue;
            notSubmitted.removeIf(branch -> (!userRepository.enabledById((String) branch)));
            map.put("notSubmitted", notSubmitted);
            tasks.add(map);
        }

        return tasks;
    }

    public Map<String, Object> getReportsStatsForDate(LocalDate date) {
        final String name = "Logboek";
        LocalDate end = date.plusDays(1);
        Map<String, Object> map = new HashMap<>();

        Optional<Task> task = this.taskRepository.findWithNameAndDate(name,date,end);

        if(task.isEmpty()) return map;

        map.put("id", task.get().get_id());
        map.put("name", task.get().getName());
        map.put("deadline", task.get().getDeadline());

        ArrayList<Object> branches = new ArrayList<>();
        for (String assignee : task.get().getAssignees()) {
            HashMap<String, Object> branch = new HashMap<>();
            branch.put("branch", assignee);
            branch.put("submitted", task.get().getSubmittedBy().contains(assignee));
            branches.add(branch);
        }
        map.put("branches", branches);
        return map;
    }

    /**
     *
     * @param taskId - Task which submission is associated to
     * @param name - Branch which submission is associated to
     * @param answer - value to be saved
     * @param i - Index in answer array
     * @return Updated version of submission
     */
    public TaskSubmission setAnswer(String taskId, String name, String answer, int i){
        TaskSubmission taskSubmission = getSubmission(taskId,name);
        taskSubmission.setAnswer(answer,i);
        taskSubmission.setLastEdit(LocalDateTime.now());
        return save(taskSubmission);
    }

    /**
     * Compares two Dates for ordering.
     *
     * @param   o1  The first Date to be compared.
     * @param   o2  The second Date to be compared.
     * @return  the value 0 if the argument Date is equal to
     *          this Date; a value less than 0 if this Date
     *          is before the Date argument; and a value greater than
     *          0 if this Date is after the Date argument.
     * @since   1.2
     * @throws    NullPointerException if {@code anotherDate} is null.
     */
    public static int compareToDate(Task o1, Task o2) {
        return o1.getDeadline().compareTo(o2.getDeadline());
    }
}
