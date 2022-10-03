package it.hva.dmci.ict.ewa.spaghetteria4.backend.controllers;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.*;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.FileService;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.TaskService;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.UserDetailsServiceImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Non restricted endpoints
 *
 * @author Sam Toxopeus && Jerome Tesselaar
 */
@RestController
@RequestMapping(value = "/api/", produces = "application/json")
public class ApiController {
    private TaskService taskService;
    private FileService fileService;
    private UserDetailsServiceImpl userDetailsService;

    public ApiController(TaskService taskService, FileService fileService, UserDetailsServiceImpl userDetailsService) {
        this.taskService = taskService;
        this.fileService = fileService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Legacy adduser function, can be accessed by anyone (even those without account!).
     * Should only be used to create an initial admin account. (Hence commented out)
     *
     * @return  Added instance of user
     */
    @GetMapping("/createAdmin")
    public User createAdmin() {
        if (userDetailsService.getUserRepository().findByUsername("admin") != null) return null;
        userDetailsService.saveDto(new UserDto("admin","admin","ADMIN",true));
        return userDetailsService.getUserRepository().findByUsername("admin");
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

    @GetMapping(value = "/createTask")
    public Task createTask() {
        ArrayList<String> test1 = new ArrayList<>();
        ArrayList<Question> test2 = new ArrayList<>();
        ArrayList<String> test3 = new ArrayList<>();
        test1.add("Amsterdam");
        test2.add(new Question("test","photo"));
        return taskService.save(Task.fromDto(new TaskDto("test", "test", new Date(), test1, test2, test3)));
    }

    @GetMapping(value = "/findBetweenTwoDates")
    public List<Task> findBetweenTwoDates(@RequestParam("branch") String branch, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        return taskService.getForBranchBetweenDates(branch ,LocalDate.parse(date1), LocalDate.parse(date2));
    }

    @GetMapping(value = "/test")
    public String test() {
        return "Hello World!";
    }
}
