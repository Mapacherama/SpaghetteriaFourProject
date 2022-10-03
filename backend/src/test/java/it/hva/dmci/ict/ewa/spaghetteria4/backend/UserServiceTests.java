package it.hva.dmci.ict.ewa.spaghetteria4.backend;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Test // Sam
    public void getAllBranchesHasNoAdmins() {
        List<User> users = new ArrayList<>(List.of(new User("Boris", "Boris", "ADMIN", true),
                new User("VanWou", "VanWou", "USER", true),
                new User("Pretorius", "Pretorius", "USER", true),
                new User("Hoofdkantoor", "Hoofdkantoor", "ADMIN", true)));

        when(userRepository.findAll()).thenReturn(new ArrayList<>(users));

        boolean adminFound = false;
        for (User u : userDetailsService.getAllBranches()) {
            adminFound = u.getRole().equals("ADMIN");

            if(adminFound) break;
        }

        Assertions.assertFalse(adminFound);
    }
}
