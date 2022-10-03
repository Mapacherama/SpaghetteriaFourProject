package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactDto {
    private @JsonProperty Long id;
    private @JsonProperty String companyName;
    private @JsonProperty String contact;
    private @JsonProperty String phone;
    private @JsonProperty String email;
    private @JsonProperty List<String> branches;

    @JsonCreator
    public ContactDto(Long id, String companyName, String contact, String phone, String email, List<String> branches) {
        this.id = id;
        this.companyName = companyName;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.branches = branches;
    }

    public ContactDto(Contact c, List<BranchContact> bc) {
        this.id = c.getId();
        this.companyName = c.getCompanyName();
        this.contact = c.getContact();
        this.phone = c.getPhone();
        this.email = c.getEmail();
        branches = new ArrayList<>();
        bc.forEach(branchContact -> branches.add(branchContact.getUser().getUsername()));
    }

    public ContactDto(Contact c, BranchContact... bc) {
        this.id = c.getId();
        this.companyName = c.getCompanyName();
        this.contact = c.getContact();
        this.phone = c.getPhone();
        this.email = c.getEmail();
        branches = new ArrayList<>();
        Arrays.stream(bc).forEach(branchContact -> branches.add(branchContact.getUser().getUsername()));
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContact() {
        return contact;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBranches() {
        return branches;
    }
}
