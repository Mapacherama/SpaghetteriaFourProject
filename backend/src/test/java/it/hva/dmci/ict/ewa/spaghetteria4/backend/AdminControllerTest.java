package it.hva.dmci.ict.ewa.spaghetteria4.backend;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.controllers.AdminController;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.UserDto;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AdminControllerTest {
    @Autowired
    private AdminController adminController;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DirtiesContext // Sam
    public void testAddingUser() {
        adminController.addUser(new UserDto("Berend", "Berend", "USER", true));

        User u = userRepository.findByUsername("Berend");

        Assertions.assertNotNull(u);
        Assertions.assertEquals("Berend", u.getUsername());
        Assertions.assertTrue(BCrypt.checkpw("Berend", u.getPassword()));
        Assertions.assertEquals("USER", u.getRole());
        Assertions.assertTrue(u.isEnabled());
    }

    @Test
    @DirtiesContext // Sam
    public void testUpdatingPassword() {
        adminController.addUser(new UserDto("Berend", "Berend", "USER", true));

        User u = userRepository.findByUsername("Berend");
        Assertions.assertNotNull(u);
        Assertions.assertEquals("Berend", u.getUsername());
        Assertions.assertTrue(BCrypt.checkpw("Berend", u.getPassword()));

        adminController.updatePassword(new UserDto("Berend", "Mark", "USER", true));

        u = userRepository.findByUsername("Berend");
        Assertions.assertNotNull(u);
        Assertions.assertEquals("Berend", u.getUsername());
        Assertions.assertTrue(BCrypt.checkpw("Mark", u.getPassword()));

    }
}
