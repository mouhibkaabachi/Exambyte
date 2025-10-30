package com.example.propra2proj.domain.repository;

import com.example.propra2proj.domain.model.ExamResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamResultRepository {

    Optional<ExamResult> findByUUID(UUID id);

    ExamResult findBySubmissionId( UUID submissionId);

    List<ExamResult> findAllByCorrectorID(UUID correctorID);

    List<ExamResult> findAllByStudentID(UUID userID);

    List<ExamResult>findDistinctBySubmissionIdAndStudentID(UUID examId, UUID studentID);

    List<ExamResult> findDistinctBySubmissionIdAndCorrectorID(UUID submissionId, UUID correctorID);

    void updateExamResultScore( Integer Score, UUID ExamResultID);


    void saveExamResult( ExamResult examResult);

}
