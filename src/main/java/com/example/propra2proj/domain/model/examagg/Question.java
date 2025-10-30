package com.example.propra2proj.domain.model.examagg;


import java.util.List;
import java.util.UUID;

public class Question {
    private final UUID id;
    private final String questionText;
    private final Integer points;
    private final Integer AccordedPoints;



    public Question(UUID id, String questionText, Integer points, Integer AccordedPoints) {
        this.id = id;
        this.questionText = questionText;
        this.points = points;
        this.AccordedPoints=AccordedPoints;
    }
    public UUID getId() {
        return id;
    }

    public String getQuestionText() {

        return questionText;
    }


    public Integer getPoints() {

        return points;
    }

    public Question CreateMultipleChoiceQuestion(UUID uuid,String questionText, Integer points ,Integer accordedPoints, List<String> Answer, Integer correctOptionIndex){
        return new MultipleChoiceQuestion(uuid,questionText,points,accordedPoints,Answer,correctOptionIndex);
    }

    public Question CreateTextQuestion(UUID uuid,String questionText, Integer points,Integer accordedPoints){
        return new TextQuestion(uuid,questionText, points,  accordedPoints);
    }


    public Integer getAccordedPoints() {
        return AccordedPoints;
    }

}