package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.enums.RecurringOptions;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Document("RecurringTask")
public class RecurringTask {
    @JsonProperty @Id private String _id;
    @JsonProperty private String name;
    @JsonProperty private String description;
    @JsonProperty private LocalDateTime startDate;
    @JsonProperty private LocalDate endDate;
    @JsonProperty private RecurringOptions intervalUnit;
    @JsonProperty private int intervalAmount;
    @JsonProperty private ArrayList<String> assignees;
    @JsonProperty private ArrayList<Question> questions;

    @PersistenceConstructor
    public RecurringTask(String name, String description, LocalDateTime startDate, LocalDate endDate, RecurringOptions intervalUnit,
                         int intervalAmount, ArrayList<String> assignees, ArrayList<Question> questions) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.intervalUnit = intervalUnit;
        this.intervalAmount = intervalAmount;
        this.assignees = assignees;
        this.questions = questions;
    }

    public RecurringTask(RecurringTaskDto recurringTaskDto) {
        this.name = recurringTaskDto.getName();
        this.description = recurringTaskDto.getDescription();
        this.startDate = recurringTaskDto.getStartDate();
        this.endDate = recurringTaskDto.getEndDate();
        this.intervalUnit = recurringTaskDto.getIntervalUnit();
        this.intervalAmount = recurringTaskDto.getIntervalAmount();
        this.assignees = recurringTaskDto.getAssignees();
        this.questions = recurringTaskDto.getQuestions();
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getAssignees() {
        return assignees;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
