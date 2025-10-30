package com.example.propra2proj.applicationlayer.mapper;

import com.example.propra2proj.applicationlayer.dtos.SubmissionDTO;
import com.example.propra2proj.domain.model.Submission;
import org.springframework.stereotype.Component;


public class SubmissionMapper {

    public Submission toSubmission(SubmissionDTO submissionDTO) {
        return new Submission(submissionDTO.submissionId(),submissionDTO.examID(), submissionDTO.studentID(),submissionDTO.studentsAnswer(),submissionDTO.correctorID());
    }

    public SubmissionDTO toSubmissionDTO(Submission submission, Integer id) {
        return new SubmissionDTO(id,submission.id(),submission.examID(),submission.studentID(),submission.studentsAnswer(),submission.correctorID());
    }
}
