package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.enums.RecurringOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecurringTaskDto {
    @JsonProperty private String name;
    @JsonProperty private String description;
    @JsonProperty private LocalDateTime startDate;
    @JsonProperty private LocalDate endDate;
    @JsonProperty private RecurringOptions intervalUnit;
    @JsonProperty private int intervalAmount;
    @JsonProperty private ArrayList<String> assignees;
    @JsonProperty private ArrayList<Question> questions;

    @JsonCreator
    public RecurringTaskDto(String name, String description, LocalDateTime startDate, LocalDate endDate, RecurringOptions intervalUnit, int intervalAmount, ArrayList<String> assignees, ArrayList<Question> questions) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intervalUnit = intervalUnit;
        this.intervalAmount = intervalAmount;
        this.assignees = assignees;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public RecurringOptions getIntervalUnit() {
        return intervalUnit;
    }

    public int getIntervalAmount() {
        return intervalAmount;
    }

    public ArrayList<String> getAssignees() {
        return assignees;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
