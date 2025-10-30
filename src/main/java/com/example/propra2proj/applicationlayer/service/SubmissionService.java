package com.example.propra2proj.applicationlayer.service;
import com.example.propra2proj.applicationlayer.exceptions.SubmissionNotFoundException;
import com.example.propra2proj.applicationlayer.exceptions.WrongInputException;
import com.example.propra2proj.domain.model.Submission;
import com.example.propra2proj.domain.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;

    }

    public SubmissionRepository getSubmissionRepository() {
        return submissionRepository;
    }

     public void saveSubmission(Submission submission) {
        submissionRepository.submit(submission);
     }

    @Transactional
    public void modifySubmission(Map<UUID,String> newAnswers, UUID submissionID) {
        if (newAnswers.toString().isBlank() || newAnswers.toString().isEmpty() || submissionID.toString().isBlank() || submissionID.toString().isEmpty()) {
            throw  new WrongInputException();
        }
        if (submissionRepository.getSubmissionById(submissionID).isEmpty()){ throw new SubmissionNotFoundException();}
        Submission Oldsubmission= submissionRepository.getSubmissionById(submissionID).get() ;
       Submission Newsubmission = new Submission(Oldsubmission.id(),Oldsubmission.examID(),
                                                 Oldsubmission.studentID(),newAnswers,Oldsubmission.correctorID());
       submissionRepository.submit(Newsubmission);
    }

    public List<Submission> getStudentsSubmissions(UUID studentsID) {
        return submissionRepository.getAllSubmissionsByStudentId(studentsID);
    }


    public List<Submission> getCorrectorsSubmissions(UUID CorrectorID) {
       return submissionRepository.getSubmissionByCorrectorID(CorrectorID) ;
    }
    public Submission getSubmissionById(UUID submissionID) {
        if (submissionRepository.getSubmissionById(submissionID).isEmpty()){ throw new SubmissionNotFoundException();}

        return submissionRepository.getSubmissionById(submissionID).get();
    }


}
