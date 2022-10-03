package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Document("task")
public class Task {
    @JsonProperty @Id
    private String _id;
    @JsonProperty private String name;
    @JsonProperty private String description;
    @JsonProperty private Date deadline;
    @JsonProperty private ArrayList<String> assignees;
    @JsonProperty private ArrayList<Question> questions;
    @JsonProperty private ArrayList<String> submittedBy;

    public static Task fromDto(TaskDto t) {
        return new Task(t.getName(), t.getDescription(), t.getDeadline(), t.getAssignees(), t.getQuestions(), t.getSubmittedBy());
    }

    public static Task fromRecurringTask(RecurringTask t, Date deadline) {
        return new Task(t.getName(),t.getDescription(),deadline,t.getAssignees(),t.getQuestions(), new ArrayList<>());
    }

    public Task(String name, String description, Date deadline, ArrayList<String> assignees, ArrayList<Question> questions, ArrayList<String> submittedBy) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.assignees = assignees;
        this.questions = questions;
        this.submittedBy = submittedBy;
    }

    public ArrayList<String> getAssignees() {
        return assignees;
    }

    public boolean addAssignee(String branch) {
        if(assignees.contains(branch)) return false;
        assignees.add(branch);
        return true;
    }

    public boolean addSubmitted(String branch) {
        if(!assignees.contains(branch) || submittedBy.contains(branch)) return false;
        submittedBy.add(branch);
        return true;
    }

    public boolean removeSubmitted(String branch) {
        if(!assignees.contains(branch) || !submittedBy.contains(branch)) return false;
        submittedBy.remove(branch);
        return true;
    }

    public String get_id() {
        return _id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getQuestionsLength() { return questions.size(); }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<String> getSubmittedBy() {
        return submittedBy;
    }

    @Override
    public String toString() {
        return "Task{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", assignees=" + assignees +
                ", questions=" + questions +
                ", submittedBy=" + submittedBy +
                '}';
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
