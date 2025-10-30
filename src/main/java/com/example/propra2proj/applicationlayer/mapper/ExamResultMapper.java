package com.example.propra2proj.applicationlayer.mapper;

import com.example.propra2proj.applicationlayer.dtos.ExamResultDTO;

import com.example.propra2proj.domain.model.ExamResult;



public class ExamResultMapper {
    public ExamResult toExamResult(ExamResultDTO ExamResultDTO){
        return new ExamResult(ExamResultDTO.uuid()
                , ExamResultDTO.submissionId() ,
                ExamResultDTO.correctorID(),
                ExamResultDTO.studentID() ,ExamResultDTO.grade() );
    }

    public ExamResultDTO toExamResultDTO(ExamResult examResult, Integer id){
        return new ExamResultDTO(id, examResult.getID() , examResult.getSubmissionID(),
                examResult.getCorrectorID(),
                examResult.getStudentID(),examResult.getGrade());
    }









}