package com.example.propra2proj.applicationlayer.dtos.examagg;

import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("Text_Question")
public class TextQuestionDTO extends QuestionDTO {


    public TextQuestionDTO(UUID uuid, String questionText, Integer points, Integer AccordedPoints) {
        super(uuid,questionText, points, AccordedPoints);
    }

}
