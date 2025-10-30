package com.example.propra2proj.domain.repository;

import com.example.propra2proj.domain.model.Submission;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SubmissionRepository {

     void updateExamCorrectorID(UUID examID, UUID newCorrectorID);

     void submit(Submission submission);

     Optional <Submission> getSubmissionById(UUID id);

     List<Submission> getAllSubmissionsByStudentId(UUID studentId);

     List<Submission> getAllSubmissionsByExamId(UUID examId);

     Map<UUID,String> getAnswersBySubmissionId(UUID submissionId);

     List<Submission> getSubmissionsByExamId( UUID examId);

     List<Submission> getSubmissionByCorrectorID( UUID correctorId);


}
