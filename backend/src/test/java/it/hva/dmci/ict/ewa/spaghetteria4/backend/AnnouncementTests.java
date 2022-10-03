package it.hva.dmci.ict.ewa.spaghetteria4.backend;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Announcement;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.AnnouncementRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.AnnouncementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementTests {
    @Autowired
    private AnnouncementService announcementService;
    @MockBean
    private AnnouncementRepository announcementRepository;

    @Test
    public void findAllOfProductsIsCorrectAmount() {
        when(announcementRepository.findAll()).thenReturn(Stream
                .of(new Announcement("Keuken Schoonmaken", "Zorg ervoor dat je goed achter de kasten schoonmaakt!", LocalDateTime.now(),
                        LocalDateTime.now().plusHours(2L)), new Announcement("Kerstspullen opruimen", "Dit moet voor 8 december gedaan zijn.", LocalDateTime.now(),
                        LocalDateTime.now().plusHours(3L))).collect(Collectors.toList()));
        Assertions.assertEquals(2, announcementService.getAllAnnouncements().size());
    }
}
