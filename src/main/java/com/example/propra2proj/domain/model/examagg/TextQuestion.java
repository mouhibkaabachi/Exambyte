
package com.example.propra2proj.domain.model.examagg;


import java.util.UUID;

public class TextQuestion extends Question {

    public TextQuestion(UUID uuid, String questionText, Integer points, Integer AccordedPoints) {
        super( uuid,questionText ,points,AccordedPoints);
    }


    }
