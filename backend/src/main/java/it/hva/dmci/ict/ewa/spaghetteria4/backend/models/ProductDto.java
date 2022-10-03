package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto {
    private @JsonProperty String name;
    private @JsonProperty String desc;
    private @JsonProperty double price;

    @JsonCreator
    public ProductDto(String name, String desc, double price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }
}
