package it.hva.dmci.ict.ewa.spaghetteria4.backend.services;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.*;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.AnnouncementRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.BranchAnnouncementRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private AnnouncementRepository announcementRepository;
    private BranchAnnouncementRepository branchAnnouncementRepository;
    private UserRepository userRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, BranchAnnouncementRepository branchAnnouncementRepository, UserRepository userRepository) {
        this.announcementRepository = announcementRepository;
        this.branchAnnouncementRepository = branchAnnouncementRepository;
        this.userRepository = userRepository;
    }

    public AnnouncementRepository getAnnouncementRepository() {
        return announcementRepository;
    }

    public Announcement addAnnouncement(AnnouncementDto a) {
        // Nieuwe announcement wordt aangemaakt op basis van de kopie object die meegegeven wordt in de parameter
        Announcement announcement = new Announcement(a.getTopic(),a.getDescription(), a.getStartDate(), a.getEndDate());
        // Nieuwe announcement wordt aan de database toegevoegd.
        announcement = announcementRepository.save(announcement);

        // loopt door de user branches heen die verbonden zijn aan de announcement.
        for (String branch : a.getBranches()) {
            // User object wordt uit de database opgehaald.
            User b = userRepository.findByUsername(branch);
            // de announcement en user wordt aan de many to many tabel toegevoegd.
            branchAnnouncementRepository.save(new BranchAnnouncement(b,announcement));
        }

        return announcement;
    }

    public List<Announcement> getForBranchBetweenDates(String branch, LocalDateTime start, LocalDateTime end) {
        // voor nu heb ik de limiet op een dag gezet, de limiet tot wanneer de berichten weergegeven zijn.
        end = end.plusDays(1);
        // Alle announcements die bij de ingelogde branche passen worden opgehaald, die tussen de gegeven start en einddatum zitten.
        List<Announcement> announcements = this.announcementRepository.findAllBetweenTwoDates(this.userRepository.findByUsername(branch), start, end);
        // De announcements worden gesorteerd op datum.
        announcements.sort(AnnouncementService::compareTo);
        // De lijst van announcements worden teruggegeven.
        return announcements;
    }

    public List<AnnouncementDto> getAllAnnouncements() {
        // Nieuwe lijst word aangemaakt waarin de announcementDtos komen te staan die opgehaald worden uit de database.
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        // Alle Announcements worden uit de database opgehaald.
        List<Announcement> announcements = announcementRepository.findAll();
        // Alle branchAnnouncements worden uit de database opgehaald.
        List<BranchAnnouncement> branchAnnouncements = branchAnnouncementRepository.findAll();
        // ForEach voert een actie uit voor elk object die in de List<Announcement> lijst staat.
        announcements.forEach(announcement -> {
            // Er wordt een nieuwe lijst aangemaakt waar BranchAnnouncements in komen te staan, gebasseerd op de referentie
            // id's van de Announcements die in de Many to Many lijst staan. Announcement Id's worden met elkaar vergeleken
            // komen ze overeen dan worden ze aan de lijst toegevoegd.
            List<BranchAnnouncement> branches = branchAnnouncements.stream().filter(branchAnnouncement ->
                    branchAnnouncement.getAnnouncement().getId().equals(announcement.getId())).collect(Collectors.toList());
            // Er wordt een kopie object gemaakt (announcementDto) waar de announcement en de lijst met branches in komen te staan.
            // Voor elk announcement object die in deze lijst staat word deze actie uitgevoerd.
            announcementDtos.add(new AnnouncementDto(announcement,branches));

        });
        // De announcementDtos lijst wordt gesorteerd op datum :: (is de referenctie naar de methode), en daarna wordt de
        // uiteindelijke lijst omgekeerd, zodat de berichten met een eerdere datum bovenaan komen te staan.
        announcementDtos.sort(this::compareTo);
        Collections.reverse(announcementDtos);

        return announcementDtos;
    }

    public List<Announcement> getAllAnnouncementsForBranch(String user) {

        return announcementRepository.findAllForUser(userRepository.findByUsername(user));
    }

    public boolean updateAnnouncement(AnnouncementDto aD) {
        // Het Announcement object word uit de database opgehaald op basis van het id van announcementDto object die in de parameter staat.
        Optional<Announcement> oA = announcementRepository.findById(aD.getId());
        // Als er geen object word teruggegeven uit de database, dan wordt er een false melding teruggegeven.
        if (oA.isEmpty()) return false;
        // Het Optional object wordt omgezet in een normaal Announcement object.
        Announcement a = oA.get();

        // Het object wordt upgedate met de informatie die uit het parameter object komt.
        a.updateFromDto(aD);

        // Het object wordt aan de announcement tabel toegevoegd in de database. Nu rest de many to many tabel nog.
        a = announcementRepository.save(a);
        // Branches die aan de announcement gekoppeld zitten, worden opgehaald uit de database.
        List<BranchAnnouncement> currentBranches = branchAnnouncementRepository.findAllByAnnouncement(a);

        // Branches die niet aan deze announcementDto gekoppeld zitten moeten verwijderd worden uit de database, dus eerst deze data
        // eruit filteren en dan aan een lijstje toevoegen zodat ze daarna verwijderd kunnen worden.

        List<BranchAnnouncement> toDelete = currentBranches.stream().filter(ba ->
                !aD.getBranches().contains(ba.getUser().getUsername())).collect(Collectors.toList());

        // Foreach voert een actie uit voor elk object in de gefilterde lijst (object genaamd brancContact en aD is het object uit de parameter)
        // en de lambda expressie verwijderd al de gebruikers uit de lijst (waar alle branches in staan in de announcementDto class) van de Dto die niet in het Dto object staan.
        toDelete.forEach(branchAnnouncement -> aD.getBranches().remove(branchAnnouncement.getUser().getUsername()));
        // De announcement object heeft de "oude" branches nog achter zich staan, die moeten uit de currentbranches lijst worden gehaald (zodat ze daarna geupdate kunnen worden).
        currentBranches.removeAll(toDelete);
        // Hier worden de "oude" branches uit de MySQL tabel verwijderd.
        branchAnnouncementRepository.deleteAll(toDelete);
        // "Oude" branches worden eruit gehaald, maar nog niet de branches die er al in staan. Dus gaan we daar een lijst
        // voor maken.
        List<BranchAnnouncement> alreadyAdded = currentBranches.stream().filter(ba ->
                aD.getBranches().contains(ba.getUser().getUsername())).collect(Collectors.toList());
        // Als de branches al voorkomen in de AnnouncementDto Branchlijst worden die van de Branch lijst afgehaald.
        alreadyAdded.forEach(branchAnnouncement -> aD.getBranches().remove(branchAnnouncement.getUser().getUsername()));
        // Foreach voert weer een actie uit voor alle branch opjecten die in de Branch list staan (List<String> branchnaam)
        // die strings kunnen weer gebruikt worden om de branches te vinden findByUsername(branch) en het totaal plaatje
        // zorgt dat de BranchAnnouncements aan de many to many tabel te kunnen toegevoegd worden.
        // Eerst de branch gebruiker object en daarna het announcement object gebasseerd op het id van het Dto object.
        aD.getBranches().forEach(branch -> branchAnnouncementRepository.save(new BranchAnnouncement(userRepository.findByUsername(branch),
                announcementRepository.findById(aD.getId()).get())));
        // Kijkt of het id voorkomt in de announcement tabel zo ja geeft een true waarde terug.
        return announcementRepository.findById(a.getId()).isPresent();
    }

    public boolean deleteAnnouncement(long id) {
        branchAnnouncementRepository.findAllByAnnouncement(announcementRepository.findById(id).get()).forEach(branchAnnouncementRepository::delete);
        announcementRepository.deleteById(id);
        return announcementRepository.findById(id).isEmpty();
    }

    public AnnouncementDto getAnnouncementDetailed(Long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);

        if (announcement.isEmpty()) return null;

        List<BranchAnnouncement> branchAnnouncements = branchAnnouncementRepository.findAllByAnnouncement(announcement.get());

        return new AnnouncementDto(announcement.get(),
                branchAnnouncements.stream().filter(ba -> ba.getAnnouncement().getId().equals(announcement.get().getId())).collect(Collectors.toList()));
    }

    public static int compareTo(Announcement o1, Announcement o2) {
        final LocalDateTime now = LocalDateTime.now();

        var o1Active = o1.getStartDate().isBefore(now) && o1.getEndDate().isAfter(now);
        var o2Active = o2.getStartDate().isBefore(now) && o2.getEndDate().isAfter(now);
        var toReturn = 0;

        if (o1Active ^ o2Active) {
            toReturn = o1Active ? 1 : -1;
        }

        if (toReturn == 0) toReturn = o1.getEndDate().compareTo(o2.getEndDate());
        if (toReturn == 0) toReturn = o1.getStartDate().compareTo(o2.getStartDate());
        if (toReturn == 0) toReturn = o1.getTopic().compareTo(o2.getTopic());

            return toReturn;
    }

    public int compareTo(AnnouncementDto o1, AnnouncementDto o2) {
        final LocalDateTime now = LocalDateTime.now();

        var o1Active = o1.getStartDate().isBefore(now) && o1.getEndDate().isAfter(now);
        var o2Active = o2.getStartDate().isBefore(now) && o2.getEndDate().isAfter(now);
        var toReturn = 0;

        if (o1Active ^ o2Active) {
            toReturn = o1Active ? 1 : -1;
        }

        if (toReturn == 0) toReturn = o1.getEndDate().compareTo(o2.getEndDate());
        if (toReturn == 0) toReturn = o1.getStartDate().compareTo(o2.getStartDate());
        if (toReturn == 0) toReturn = o1.getTopic().compareTo(o2.getTopic());

        return toReturn;
    }
}
