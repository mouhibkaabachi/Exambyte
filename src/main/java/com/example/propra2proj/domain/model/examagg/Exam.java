package com.example.propra2proj.domain.model.examagg;

import java.util.List;
import java.util.UUID;


public class Exam {

    private final UUID id;
    private final String name;
    private final Integer overallScore;
    private final List<Question> questions;
    private final UUID organiserID;
    private final List<UUID> AssignedStudentsId;
    private final boolean isCompleted;



    public Exam(UUID id, String name,
                Integer overallScore,
                List<Question> questions,
                UUID organiserId,
                List<UUID> AssignedStudentsId,
                boolean isCompleted
    ) {
        this.id = id;
        this.name = name;
        this.overallScore = overallScore;
        this.questions = questions;
        this.organiserID = organiserId;
        this.AssignedStudentsId = AssignedStudentsId;
        this.isCompleted = isCompleted;

    }

    public UUID getId() {
        return id;

    }

    public String getName() {
        return name;
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public UUID getOrganiserID() {
        return organiserID;
    }

    public List<UUID> getAssignedStudentsId() {
        return AssignedStudentsId;
    }



    public boolean isCompleted() {
        return isCompleted;
    }
}