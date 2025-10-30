package com.example.propra2proj.applicationlayer.dtos.examagg;

import java.util.UUID;

public abstract class QuestionDTO {

    private final UUID uuid;
    private final String questionText;
    private final Integer points;
    private final Integer AccordedPoints;

    public QuestionDTO(UUID uuid,String questionText, Integer points, Integer AccordedPoints) {
        this.questionText = questionText;
        this.points = points;
        this.AccordedPoints = AccordedPoints;
        this.uuid = uuid;
    }
    public UUID getUuid() {
        return uuid;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Integer getAccordedPoints() {
        return AccordedPoints;
    }

    public Integer getPoints() {
        return points;
    }


}
