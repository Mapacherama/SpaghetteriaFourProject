package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;

public class TaskDto {
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private Date deadline;
    @JsonProperty
    private ArrayList<String> assignees;
    @JsonProperty
    private ArrayList<Question> questions;
    @JsonProperty
    private ArrayList<String> submittedBy;

    @JsonCreator
    public TaskDto(String name, String description, Date deadline, ArrayList<String> assignees, ArrayList<Question> questions, ArrayList<String> submittedBy) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.assignees = assignees;
        this.questions = questions;
        this.submittedBy = submittedBy;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<String> getSubmittedBy() {
        return submittedBy;
    }

    public ArrayList<String> getAssignees() {
        return assignees;
    }
}
