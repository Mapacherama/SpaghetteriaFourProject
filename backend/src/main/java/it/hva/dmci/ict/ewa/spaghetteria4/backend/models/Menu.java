package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Menu implements Serializable {

    @ManyToOne
    private @JsonProperty Product product;

    private @JsonProperty String menuName;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Menu() {
    }

    public Product getProduct() {
        return product;
    }

    public String getMenuName() {
        return menuName;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "product=" + product +
                ", menuName='" + menuName + '\'' +
                ", id=" + id +
                '}';
    }
}
