package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document("tasksubmission")
public class TaskSubmission {
    @JsonProperty @Id
    private String _id;
    @JsonProperty
    private String task;
    @JsonProperty
    private String branch;
    @JsonProperty
    private String[] answers;
    @JsonProperty
    private LocalDateTime lastEdit;

    @JsonCreator
    public TaskSubmission(String task, String branch, String[] answers, LocalDateTime lastEdit) {
        this.task = task;
        this.branch = branch;
        this.answers = answers;
        this.lastEdit = lastEdit;
    }

    public void setAnswer(String s, int i) {
        answers[i] = s;
    }

    public void setLastEdit(LocalDateTime lastEdit) {
        this.lastEdit = lastEdit;
    }
}
