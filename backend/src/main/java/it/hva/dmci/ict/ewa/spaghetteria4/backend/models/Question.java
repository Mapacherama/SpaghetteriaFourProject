package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Question {
    @JsonProperty
    private String name;
    @JsonProperty
    private String type;
    @JsonProperty
    private ArrayList<String> options;

    @JsonCreator
    public Question(String name, String type, ArrayList<String> options) {
        this.name = name;
        this.type = type;
        this.options = options;
    }

    public Question(String name, String type) {
        this(name,type,null);
    }

    public Question() {
    }
}
