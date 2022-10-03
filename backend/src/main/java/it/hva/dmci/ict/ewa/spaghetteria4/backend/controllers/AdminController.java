package it.hva.dmci.ict.ewa.spaghetteria4.backend.controllers;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.*;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Endpoints only accessible by users with the role ADMIN
 *
 * @author Sam Toxopeus && Jerome Tesselaar
 */
@RestController
@RequestMapping(value = "/admin/", produces = "application/json")
public class AdminController {

    private final UserDetailsServiceImpl userDetailsService;
    private final TaskService taskService;
    private final ContactService contactService;
    private final ProductService productService;
    private final AnnouncementService announcementService;

    public AdminController(UserDetailsServiceImpl userDetailsService, TaskService taskService, ContactService contactService, ProductService productService, AnnouncementService announcementService) {
        this.userDetailsService = userDetailsService;
        this.taskService = taskService;
        this.contactService = contactService;
        this.productService = productService;
        this.announcementService = announcementService;
    }

    @GetMapping(value = "/user/get")
    public String getUser(@RequestParam("user") String user) {
        User u = userDetailsService.getUserRepository().findByUsername(user);

        if (u != null) return u.toString();
        else return "Not found.";
    }

    @PostMapping(value = "/user/add")
    public String addUser(@RequestBody UserDto u) {
        if (userDetailsService.saveDto(u)) {
            return userDetailsService.loadUserByUsername(u.getUsername()).toString();
        }
        return "Something went wrong...";
    }

    @PostMapping(value = "/user/update")
    public String updateUser(@RequestBody UserDto u) {
        if (userDetailsService.updateInfoDto(u)) {
            return userDetailsService.loadUserByUsername(u.getUsername()).toString();
        }
        return "Something went wrong...";
    }

    @PostMapping(value = "/user/updatePassword")
    public String updatePassword(@RequestBody UserDto u) {
        if (userDetailsService.updatePasswordDto(u)) {
            return userDetailsService.loadUserByUsername(u.getUsername()).toString();
        }
        return "Something went wrong...";
    }

    @GetMapping(value = "/task/getAllFor")
    public List<Task> getAllTasks(@RequestParam("branch") String branchName) {
        return taskService.findByAssignee(branchName);
    }

    @GetMapping(value = "/task/getAll")
    public List<Task> getAllTasks() {
        return taskService.getTaskRepository().findAll();
    }

    @GetMapping(value = "/task/getAllRecurring")
    public List<RecurringTask> getAllRecurringTasks() {
        return taskService.getRecurringTaskRepository().findAll();
    }


    @GetMapping(value = "/task/getUnfinishedFor")
    public List<Task> getUnfinishedTasksFor(@RequestParam("branch") String branchName) {
        return taskService.findUnfinishedByAssignee(branchName);
    }

    @GetMapping(value = "/task/getOutstandingPastDeadline")
    public List<Map<String, Object>> getUnfinishedPastDeadlineFor(@RequestParam("date") String date) {
        return taskService.getPastDueTasks(LocalDate.parse(date));
    }

    @GetMapping(value = "/task/submission/getBetweenPeriodFor")
    public List<Task> getTaskSubmissionById(@RequestParam("branch") String branch, @RequestParam("date1") String date1, @RequestParam("date2") String date2, Authentication authentication) {
        return taskService.getForBranchBetweenDates(branch, LocalDate.parse(date1), LocalDate.parse(date2));
    }

    @GetMapping(value = "/task/submission/getById")
    public TaskSubmission getTaskSubmissionById(@RequestParam("task") String taskId, @RequestParam("branch") String branch) {
        return taskService.getSubmission(taskId, branch);
    }

    @PostMapping(value = "/task/submit")
    public Task submitTask(@RequestParam("task") String taskId, @RequestParam("branch") String branch) {
        return taskService.submit(taskId, branch);
    }

    @PostMapping(value = "/task/update")
    public Task createTask(@RequestParam("task") String taskId, @RequestBody TaskDto t) {
        Task task = Task.fromDto(t);
        task.set_id(taskId);
        return taskService.save(task);
    }

    @GetMapping(value = "/task/recurring/get")
    public RecurringTask getRecurringTask(@RequestParam("task") String taskId) {
        return taskService.getRecurringTaskRepository().findById(taskId).orElse(null);
    }

    @PostMapping(value = "/task/recurring/update")
    public RecurringTask updateRecurringTask(@RequestBody RecurringTask t) {
        return taskService.getRecurringTaskRepository().save(t);
    }

    @GetMapping(value = "/reports/getStatsForDay")
    public Map<String, Object> getReportStatsForDay(@RequestParam("date") String date) {
        return taskService.getReportsStatsForDate(LocalDate.parse(date));
    }

    @PostMapping(value = "/createProduct")
    public Product addProduct(@RequestBody Product p) {
        return productService.save(p);
    }

    @PostMapping(value = "/task/createRecurring")
    public RecurringTask createTask(@RequestBody RecurringTaskDto t) {
        System.out.println(t.getStartDate().toString());
        System.out.println(t.getEndDate());

        RecurringTask recurringTask = new RecurringTask(t);

        Date deadline = new Date();
        deadline.setTime(recurringTask.getStartDate().toLocalTime().toEpochSecond(LocalDate.now(), ZoneOffset.UTC) * 1000);

        if (t.getStartDate().toLocalDate().isEqual(LocalDate.now()))
            taskService.getTaskRepository().save(Task.fromRecurringTask(recurringTask, deadline));
        return taskService.getRecurringTaskRepository().save(new RecurringTask(t));
    }

    @GetMapping(value = "/getAllBranches")
    public String getAllBranches() {
        return userDetailsService.getAllActiveBranches().toString();
    }

    @GetMapping(value = "/getAllUsers")
    public String getAllUsers(@RequestParam("activeOnly") boolean activeOnly, @RequestParam("adminOnly") boolean adminOnly) {
        return userDetailsService.getAllUsers(activeOnly, adminOnly).toString();
    }

    @GetMapping(value = "/getAllContactsForBranch")
    public List<Contact> getAllContacts(@RequestParam("branch") String branch) {
        return contactService.getAllContactsForBranch(branch);
    }

    @GetMapping(value = "/getAllProducts")
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/getAllProductsForCategory")
    public List<Product> findAllForCategory(@RequestParam("category") String category) {
        return productService.getAllProductsForCategory(category);
    }

    @PostMapping("/updateAnnouncement")
    public boolean updateProduct(@RequestBody AnnouncementDto a) {
        return announcementService.updateAnnouncement(a);
    }

    @DeleteMapping("/deleteProductById")
    public boolean deleteProductById(@RequestParam Long id) {
        return productService.deleteProductById(id);
    }

    /**
     * Quick way to check if you are authorized to interact with the admin api
     *
     * @return Hello World :)
     */
    @GetMapping("/test")
    public String test() {
        return "Hello, World!";
    }

    @GetMapping("/getAllContacts")
    public List<ContactDto> findALlContacts() {
        return contactService.getAllContactsDetailed();
    }

    @GetMapping("/getContactById")
    public ContactDto findContactById(@RequestParam Long id) {
        return contactService.getContactDetailed(id);
    }

    @PostMapping("/updateContact")
    public boolean updateContact(@RequestBody ContactDto c) {
        return contactService.updateContact(c);
    }

    @GetMapping("/deleteContact")
    public boolean deleteContact(@RequestParam Long id) {
        return contactService.deleteContact(id);
    }

    @PostMapping("/addContact")
    public boolean deleteContact(@RequestBody ContactDto c) {
        return contactService.addContact(c);
    }

    @PostMapping("/dish/update")
    public Product updateContact(@RequestBody Product p) {
        return productService.save(p);
    }

    @GetMapping("/getAllAnnouncements")
    public List<AnnouncementDto> findALlAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    @GetMapping(value = "/getAllAnnouncementsForBranch")
    public List<Announcement> getAllAnnouncements(@RequestParam("branch") String branch) {
        return announcementService.getAllAnnouncementsForBranch(branch);
    }

    @GetMapping(value = "/getAnnouncementBetweenPeriodFor")
    public List<Announcement> getAnnouncementSubmissionBetweenPeriod(@RequestParam("branch") String branch, @RequestParam("date1") String date1, @RequestParam("date2") String date2, Authentication authentication) {
        return announcementService.getForBranchBetweenDates(branch, LocalDateTime.parse(date1), LocalDateTime.parse(date2));
    }

    @PostMapping("/addAnnouncement")
    public Announcement addAnnouncement(@RequestBody AnnouncementDto a) {
        return announcementService.addAnnouncement(a);
    }

    @GetMapping("/getAnnouncementById")
    public AnnouncementDto findAnnouncementById(@RequestParam Long id) {
        return announcementService.getAnnouncementDetailed(id);
    }

    @GetMapping("/deleteAnnouncement")
    public boolean deleteAnnouncement(@RequestParam Long id) {
        return announcementService.deleteAnnouncement(id);
    }

}
