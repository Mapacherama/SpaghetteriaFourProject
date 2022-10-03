package it.hva.dmci.ict.ewa.spaghetteria4.backend.services;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.BranchContact;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Contact;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.ContactDto;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.User;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.BranchContactRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.ContactRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {
    private ContactRepository contactRepository;
    private BranchContactRepository branchContactRepository;
    private UserRepository userRepository;

    public ContactService(ContactRepository contactRepository, BranchContactRepository branchContactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.branchContactRepository = branchContactRepository;
        this.userRepository = userRepository;
    }

    /**
     *
     * @param user - Branch to get contacts for
     * @return ALl contacts for branch
     */
    public List<Contact> getAllContactsForBranch(String user) {
        List<Contact> contacts = contactRepository.findAllForUser(userRepository.findByUsername(user));
        return contacts;
    }

    /**
     *
     * @return All contacts in database
     */
    public List<ContactDto> getAllContactsDetailed() {
        List<ContactDto> contactDtos = new ArrayList<>();
        List<Contact> contacts = contactRepository.findAll();
        List<BranchContact> branchContacts = branchContactRepository.findAll();

        contacts.forEach(contact -> {
            List<BranchContact> branches = branchContacts.stream().filter(branchContact ->
                    branchContact.getContact().getId().equals(contact.getId())).collect(Collectors.toList());
            contactDtos.add(new ContactDto(contact,branches));

        });

        return contactDtos;
    }

    public ContactDto getContactDetailed(Long id) {
        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isEmpty()) return null;

        List<BranchContact> branchContacts = branchContactRepository.findAllByContact(contact.get());

        return new ContactDto(contact.get(),
                branchContacts.stream().filter(bc -> bc.getContact().getId().equals(contact.get().getId())).collect(Collectors.toList()));
    }

    public boolean updateContact(ContactDto cD) {
        Optional<Contact> oC = contactRepository.findById(cD.getId());
        if (oC.isEmpty()) return false;
        Contact c = oC.get();

        c.updateFromDto(cD);

        c = contactRepository.save(c);

        List<BranchContact> currentBranches = branchContactRepository.findAllByContact(c);

        List<BranchContact> toDelete = currentBranches.stream().filter(bc ->
                !cD.getBranches().contains(bc.getUser().getUsername())).collect(Collectors.toList());

        toDelete.forEach(branchContact -> cD.getBranches().remove(branchContact.getUser().getUsername()));
        currentBranches.removeAll(toDelete);
        branchContactRepository.deleteAll(toDelete);

        List<BranchContact> alreadyAdded = currentBranches.stream().filter(bc ->
                cD.getBranches().contains(bc.getUser().getUsername())).collect(Collectors.toList());

        alreadyAdded.forEach(branchContact -> cD.getBranches().remove(branchContact.getUser().getUsername()));

        cD.getBranches().forEach(branch -> branchContactRepository.save(new BranchContact(userRepository.findByUsername(branch),
                contactRepository.findById(cD.getId()).get())));

        return contactRepository.findById(c.getId()).isPresent();
    }

    public boolean deleteContact(long id) {
        branchContactRepository.findAllByContact(contactRepository.findById(id).get()).forEach(branchContactRepository::delete);
        contactRepository.deleteById(id);
        return contactRepository.findById(id).isEmpty();
    }

    public boolean addContact(ContactDto c) {
        Contact contact = new Contact(c.getCompanyName(),c.getContact(),c.getPhone(),c.getEmail());
        contact = contactRepository.save(contact);

        for (String branch : c.getBranches()) {
            User b = userRepository.findByUsername(branch);
            branchContactRepository.save(new BranchContact(b,contact));
        }

        return contactRepository.findById(contact.getId()).isPresent();
    }
}
