package com.example.propra2proj.domain.model.examagg;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MultipleChoiceQuestion extends Question {

    private final List<String> options ;
    private Integer correctOptionIndex;



    public MultipleChoiceQuestion(UUID uuid,String questionText, Integer points, Integer AccordedPoints , List<String> options , Integer correctOptionIndex) {
        super(uuid,questionText,points,AccordedPoints);

        this.options=options;
        this.correctOptionIndex = correctOptionIndex;

    }

    public Integer getCorrectOptionIndex() {
        return correctOptionIndex;
    }
    void setCorrectOptionIndex(Integer correctOptionIndex)

    {   this.correctOptionIndex=correctOptionIndex;

    }

     public List<String> getOptions() {

         return options;
     }



     public Integer ScoreAnswer(Integer CorrectOptionIndex, Integer StudentAnswer ){
         if (Objects.equals(CorrectOptionIndex, StudentAnswer)){ return this.getPoints(); }
         else {return 0;}
    }
 }