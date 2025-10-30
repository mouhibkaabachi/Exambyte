package com.example.propra2proj.applicationlayer.mapper;

import com.example.propra2proj.applicationlayer.dtos.examagg.ExamDTO;
import com.example.propra2proj.domain.model.examagg.Exam;


public class ExamMapper {

    public Exam toexam(ExamDTO examDTO) {
        return new Exam(examDTO.uuid(),examDTO.name(),
                examDTO.overallScore(),examDTO.questions().stream().map(QuestionMapper::toQuestion).toList(),
                examDTO.organiserID() ,
                 examDTO.AssignedStudentsId(),
                examDTO.isCompleted());
    }
    public ExamDTO toexamDTO( Exam exam, Integer id) {
        return new ExamDTO(id,
                           exam.getName(),
                           exam.getId(),
                           exam.getOverallScore(),
                           exam.getQuestions().stream().map(QuestionMapper::toQuestionDTO).toList(),
               exam.getOrganiserID() ,
                 exam.getAssignedStudentsId(), exam.isCompleted() );






    }
}