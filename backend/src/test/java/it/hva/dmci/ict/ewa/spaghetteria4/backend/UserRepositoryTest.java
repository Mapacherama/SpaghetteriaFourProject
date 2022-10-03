package it.hva.dmci.ict.ewa.spaghetteria4.backend;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DirtiesContext // Sam
    public void testFindingUserByUsername() {
        userRepository.save(new User("Boris", "Boris", "ADMIN", true));

        Assertions.assertEquals("Boris", userRepository.findByUsername("Boris").getUsername());
        Assertions.assertNull(userRepository.findByUsername("Berend"));
    }

    @Test
    @DirtiesContext // Sam
    public void testChangingRole() {
        userRepository.save(new User("Boris", "Boris", "ADMIN", true));

        User u = userRepository.findByUsername("Boris");
        Assertions.assertEquals("ADMIN", userRepository.findByUsername("Boris").getRole());
        u.setAuthority("USER");
        userRepository.save(u);

        Assertions.assertEquals("USER", userRepository.findByUsername("Boris").getRole());
    }

}
