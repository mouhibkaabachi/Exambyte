package com.example.propra2proj.applicationlayer.mapper;


import com.example.propra2proj.applicationlayer.dtos.examagg.MultipleChoiceQuestionDTO;
import com.example.propra2proj.applicationlayer.dtos.examagg.QuestionDTO;
import com.example.propra2proj.applicationlayer.dtos.examagg.TextQuestionDTO;
import com.example.propra2proj.domain.model.examagg.MultipleChoiceQuestion;
import com.example.propra2proj.domain.model.examagg.Question;
import com.example.propra2proj.domain.model.examagg.TextQuestion;
import org.springframework.stereotype.Component;


public class QuestionMapper {
    public static QuestionDTO toQuestionDTO(Question Question){
        if (Question instanceof MultipleChoiceQuestion){
            return toMultipleChoiceQuestionDTO((MultipleChoiceQuestion) Question);
        } else if (Question instanceof TextQuestion) {
            return toTextQuestionDTO((TextQuestion) Question);
        }
        else return null;}

    public static MultipleChoiceQuestionDTO toMultipleChoiceQuestionDTO(MultipleChoiceQuestion multipleChoiceQuestion){
        return new MultipleChoiceQuestionDTO(multipleChoiceQuestion.getId(),multipleChoiceQuestion.getQuestionText(),multipleChoiceQuestion.getPoints(),
                multipleChoiceQuestion.getAccordedPoints(),multipleChoiceQuestion.getOptions(),multipleChoiceQuestion.getCorrectOptionIndex());
    }

    public static TextQuestionDTO toTextQuestionDTO( TextQuestion textQuestion){
        return new TextQuestionDTO(textQuestion.getId(),textQuestion.getQuestionText(),textQuestion.getPoints(),textQuestion.getAccordedPoints());
    }


    public static Question toQuestion(QuestionDTO QuestionDTO){
        if (QuestionDTO instanceof MultipleChoiceQuestionDTO){
            return toMultipleChoiceQuestion((MultipleChoiceQuestionDTO) QuestionDTO);
        } else if (QuestionDTO instanceof TextQuestionDTO) {
            return toTextQuestion((TextQuestionDTO) QuestionDTO);
        }
        else return null;}

    public static MultipleChoiceQuestion toMultipleChoiceQuestion(MultipleChoiceQuestionDTO multipleChoiceQuestionDTO){
        return new MultipleChoiceQuestion(multipleChoiceQuestionDTO.getUuid(),multipleChoiceQuestionDTO.getQuestionText(),multipleChoiceQuestionDTO.getPoints(),
                multipleChoiceQuestionDTO.getAccordedPoints(),multipleChoiceQuestionDTO.getOptions(),
                multipleChoiceQuestionDTO.getCorrectOptionIndex());
    }

    public static TextQuestion toTextQuestion( TextQuestionDTO textQuestionDTO){
        return new TextQuestion(textQuestionDTO.getUuid(),textQuestionDTO.getQuestionText(),textQuestionDTO.getPoints(),textQuestionDTO.getAccordedPoints());
    }


}
