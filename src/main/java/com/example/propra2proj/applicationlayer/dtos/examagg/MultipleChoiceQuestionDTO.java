package com.example.propra2proj.applicationlayer.dtos.examagg;


import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table("multiple_choice_table")
public class MultipleChoiceQuestionDTO extends QuestionDTO {
    private Integer correctOptionIndex;
    private final List<String> options;


    public MultipleChoiceQuestionDTO(UUID uuid, String questionText, Integer points, Integer AccordedPoints, List<String> options, Integer correctOptionIndex) {
        super(uuid,questionText, points, AccordedPoints);
        this.options=options ;
        this.correctOptionIndex=correctOptionIndex;}

    public Integer getCorrectOptionIndex() {
        return correctOptionIndex;
    }


    public void setCorrectOptionIndex(Integer correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public List<String> getOptions() {
        return options;
    }
}
