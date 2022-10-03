package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Contact {
    private @JsonProperty String companyName;
    private @JsonProperty String contact;
    private @JsonProperty String phone;
    private @JsonProperty String email;

    public Contact() {
    }

    @JsonCreator
    public Contact(String name, String address, String phone, String email) {
        this.companyName = name;
        this.contact = address;
        this.phone = phone;
        this.email = email;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "CompanyName")
    public String getCompanyName() {
        return companyName;
    }

    @Column(name = "Contact")
    public String getContact() {
        return contact;
    }

    @Column(name = "Phone")
    public String getPhone() {
        return phone;
    }

    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public Contact updateFromDto(ContactDto contactDto) {
        companyName = contactDto.getCompanyName();
        contact = contactDto.getContact();
        email = contactDto.getEmail();
        phone =contactDto.getPhone();

        return this;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "companyName='" + companyName + '\'' +
                ", contact='" + contact + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
