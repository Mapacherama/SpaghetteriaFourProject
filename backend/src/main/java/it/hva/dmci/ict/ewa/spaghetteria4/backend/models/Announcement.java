package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Announcement {

    private @JsonProperty
    String topic;
    private @JsonProperty
    String description;
    private @JsonProperty
    LocalDateTime startDate;
    private @JsonProperty
    LocalDateTime endDate;

    @JsonCreator
    public Announcement(String topic, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.topic = topic;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Announcement() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Long getId() {
        return id;
    }

    public Announcement updateFromDto(AnnouncementDto announcementDto) {
        topic = announcementDto.getTopic();
        description = announcementDto.getDescription();
        startDate = announcementDto.getStartDate();
        endDate = announcementDto.getEndDate();

        return this;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
