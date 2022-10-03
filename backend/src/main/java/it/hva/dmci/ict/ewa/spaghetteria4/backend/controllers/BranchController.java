package it.hva.dmci.ict.ewa.spaghetteria4.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.*;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Endpoints only accessible by users with the role USER or ADMIN
 *
 * @author Sam Toxopeus && Jerome Tesselaar
 */
@RestController
@RequestMapping(value = "/branch/", produces = "application/json")
public class BranchController {
    private final TaskService taskService;
    private final FileService fileService;
    private final ContactService contactService;
    private final ProductService productService;
    private final AnnouncementService announcementService;


    public BranchController(TaskService taskService, FileService fileService, ContactService contactService, ProductService productService, AnnouncementService announcementService) {
        this.taskService = taskService;
        this.fileService = fileService;
        this.contactService = contactService;
        this.productService = productService;
        this.announcementService = announcementService;
    }

    @GetMapping(value = "/task/getAll")
    public List<Task> getAllTasks(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return taskService.findByAssignee(authentication.getName());
    }

    @GetMapping(value = "/task/getUnfinished")
    public List<Task> getUnfinishedTasksFor(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        return taskService.findUnfinishedByAssignee(authentication.getName());
    }

    @GetMapping(value = "/task/getById")
    public Task getTaskById(@RequestParam("task") String taskId) {
        return taskService.getTaskRepository().findBy_id(taskId);
    }

    @PostMapping(value = "/task/submit")
    public Task submitTask(@RequestParam("task") String taskId, Authentication authentication) {
        return taskService.submit(taskId, authentication.getName());
    }

    @PostMapping(value = "/task/unsubmit")
    public Task unsubmitTask(@RequestParam("task") String taskId, Authentication authentication) {
        return taskService.unsubmit(taskId, authentication.getName());
    }

    @PostMapping(value = "/task/create")
    public Task createTask(@RequestBody TaskDto t) {
        return taskService.saveDto(t);
    }

    @GetMapping(value = "/task/submission/getById")
    public TaskSubmission getTaskSubmissionById(@RequestParam("task") String taskId, Authentication authentication) {
        return taskService.getSubmission(taskId, authentication.getName());
    }

    @GetMapping(value = "/task/submission/getBetweenPeriod")
    public List<Task> getTaskSubmissionById(@RequestParam("date1") String date1, @RequestParam("date2") String date2, Authentication authentication) {
        return taskService.getForBranchBetweenDates(authentication.getName(), LocalDate.parse(date1), LocalDate.parse(date2));
    }

    @PostMapping(value = "/task/submission/setanswer")
    public TaskSubmission createTask(@RequestBody JsonNode s) {
        return taskService.setAnswer(s.get("task").textValue(),s.get("branch").textValue(),s.get("answer").textValue(),s.get("index").intValue());
    }

    @PostMapping(value = "/task/createMultiple")
    public List<Task> createTasks(@RequestBody List<TaskDto> t) {
        for (TaskDto t2 : t) {
            taskService.saveDto(t2);
        }
        return taskService.getTaskRepository().findAll();
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile file) {
        try {
            return fileService.uploadImage(file).toString();
        } catch (NullPointerException e) {
            return "Not found";
        }
    }

    @PostMapping(value = "/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return fileService.uploadFile(file).toString();
        } catch (NullPointerException e) {
            return "Not found";
        }
    }

    @GetMapping(value = "/getImage", produces = "image/jpeg")
    public HttpEntity<byte[]> getImage(@RequestParam("id") Long id) {
        return new HttpEntity<>(fileService.getImage(id).getBytes());
    }

    @GetMapping(value = "/getFile")
    public HttpEntity<byte[]> getImage(@RequestParam("id") Long id, HttpServletResponse response) {
        File file = fileService.getFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

        return new HttpEntity<byte[]>(file.getBytes(), headers);
    }

    @GetMapping(value = "/getContacts")
    public List<Contact> getContacts(Authentication authentication) {
        return contactService.getAllContactsForBranch((String) authentication.getName());
    }

    @GetMapping(value = "/getAnnouncements")
    public List<Announcement> getAnnouncements(Authentication authentication) {
        return announcementService.getAllAnnouncementsForBranch((String) authentication.getName());
    }

    @GetMapping(value = "/getAnnouncementsBetweenPeriod")
    public List<Announcement> getAnnouncementSubmissionBetweenDate(@RequestParam("date1") String date1, @RequestParam("date2") String date2, Authentication authentication) {
        return announcementService.getForBranchBetweenDates(authentication.getName(), LocalDateTime.parse(date1), LocalDateTime.parse(date2));
    }

    @GetMapping(value = "/getAllProducts")
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/product/getById")
    public Product getAll(@RequestParam("id") Long id) {
        Optional<Product> product = productService.getProductRepository().findById(id);
        return product.orElse(null);
    }

    @PostMapping(value = "/generateMenu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<byte[]> generateMenu(@RequestBody List<ProductDto> p, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=menu.xlsx");

        try {
            return new HttpEntity<>(productService.generateMenu(p), headers);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

