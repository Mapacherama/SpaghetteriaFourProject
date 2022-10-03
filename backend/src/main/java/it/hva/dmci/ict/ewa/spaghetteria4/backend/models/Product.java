package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Product {
    private @JsonProperty
    String productName;
    private @JsonProperty
    String price;
    public @JsonProperty
    String type;
    public @JsonProperty
    String description;
    public @JsonProperty
    boolean disabled;


    public Product() {
    }

    public Product(String productName, String price, String type, String description, boolean disabled) {
        this.productName = productName;
        this.price = price;
        this.type = type;
        this.description = description;
        this.disabled = disabled;
    }

    @Id
    @Column(name = "id")
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String getProductName() {
        return productName;
    }


    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }


    public String getPrice() {
        return price;
    }


    public boolean getDisabled() {
        return disabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", disabled=" + disabled +
                ", id=" + id +
                '}';
    }
}
