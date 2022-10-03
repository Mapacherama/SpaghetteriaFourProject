package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class BranchAnnouncement implements Serializable {

    public BranchAnnouncement() {
    }

    public BranchAnnouncement(User user, Announcement announcement) {
        this.user = user;
        this.announcement = announcement;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Announcement announcement;

    public User getUser() {
        return user;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }
}
