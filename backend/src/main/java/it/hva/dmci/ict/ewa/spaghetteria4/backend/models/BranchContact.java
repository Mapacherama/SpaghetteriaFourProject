package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class BranchContact implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Contact contact;

    public User getUser() {
        return user;
    }

    public Contact getContact() {
        return contact;
    }

    public BranchContact() {
    }

    public BranchContact(User user, Contact contact) {
        this.user = user;
        this.contact = contact;
    }
}
