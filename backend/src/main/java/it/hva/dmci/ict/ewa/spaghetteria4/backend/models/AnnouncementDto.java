package it.hva.dmci.ict.ewa.spaghetteria4.backend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnnouncementDto {
    private @JsonProperty String topic;
    private @JsonProperty String description;
    private @JsonProperty LocalDateTime startDate;
    private @JsonProperty LocalDateTime endDate;
    private @JsonProperty
    List<String> branches;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonCreator
    public AnnouncementDto(String topic, String description, LocalDateTime startDate, LocalDateTime endDate, List<String> branches, Long id) {
        this.topic = topic;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.branches = branches;
        this.id = id;
    }


    public AnnouncementDto(Announcement a, List<BranchAnnouncement> ba) {
        this.id = a.getId();
        this.topic = a.getTopic();
        this.description = a.getDescription();
        this.startDate = a.getStartDate();
        this.endDate = a.getEndDate();
        branches = new ArrayList<>();
        ba.forEach(branchAnnouncement -> branches.add(branchAnnouncement.getUser().getUsername()));
    }

    public AnnouncementDto(Announcement a, BranchAnnouncement... ba) {
        this.id = a.getId();
        this.topic = a.getTopic();
        this.description = a.getDescription();
        this.startDate = a.getStartDate();
        this.endDate = a.getEndDate();
        branches = new ArrayList<>();
        Arrays.stream(ba).forEach(branchAnnouncement -> branches.add(branchAnnouncement.getUser().getUsername()));
    }

    public Long getId() {
        return id;
    }

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

    public List<String> getBranches() {
        return branches;
    }
}
