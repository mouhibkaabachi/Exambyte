package com.example.propra2proj.applicationlayer.service;

import com.example.propra2proj.applicationlayer.exceptions.ExamNotFoundException;
import com.example.propra2proj.applicationlayer.exceptions.ExamResultNotFoundException;
import com.example.propra2proj.applicationlayer.exceptions.UserNotFoundException;
import com.example.propra2proj.applicationlayer.exceptions.WrongInputException;
import com.example.propra2proj.domain.model.ExamResult;
import com.example.propra2proj.domain.repository.ExamResultRepository;
import com.example.propra2proj.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final UserRepository userRepository;

    public ExamResultService(ExamResultRepository examResultRepository, UserRepository userRepository) {
        this.examResultRepository = examResultRepository;
        this.userRepository = userRepository;
    }

    public ExamResultRepository getExamResultRepository() {
        return examResultRepository;
    }

    public ExamResult getExamResultsBySubmissionId(UUID submissionId) {
        if (submissionId.toString().isBlank()){throw new WrongInputException();}
        if(examResultRepository.findByUUID(submissionId).isEmpty()){throw new ExamNotFoundException();}
        return examResultRepository.findBySubmissionId(submissionId) ;
    }

    public void createExamResult( UUID studentID, UUID submissionId, UUID correctorID, Integer grade){
        if (submissionId.toString().isBlank()){throw new WrongInputException();}
        if(examResultRepository.findByUUID(submissionId).isEmpty()){throw new ExamNotFoundException();}
        if (studentID.toString().isBlank()){throw new WrongInputException();}
        if(examResultRepository.findAllByStudentID(studentID).isEmpty()){throw new ExamNotFoundException();}
        if (correctorID.toString().isBlank()){throw new WrongInputException();}
        if(examResultRepository.findAllByCorrectorID(correctorID).isEmpty()){throw new ExamNotFoundException();}
        ExamResult examResult= new ExamResult (UUID.randomUUID(), studentID, submissionId, correctorID,grade);
        examResultRepository.saveExamResult(examResult);

    }

    public List<ExamResult> getExamResultByCorrectorId(UUID CorrectorId) {
        if (CorrectorId.toString().isBlank()){throw new WrongInputException();}
        if (userRepository.findById(CorrectorId).isEmpty()){throw new UserNotFoundException();}
                return examResultRepository.findAllByCorrectorID(CorrectorId);}

    public List<ExamResult> getExamResultByStudentId(UUID StudentId) {
        if (StudentId.toString().isBlank()){throw new WrongInputException();}
        if (userRepository.findById(StudentId).isEmpty()){throw new UserNotFoundException();}
        return examResultRepository.findAllByStudentID(StudentId);
    }
    public ExamResult getExamResultById(UUID examResultId) {
        if (examResultId.toString().isBlank()){throw new WrongInputException();}
        if (examResultRepository.findByUUID(examResultId).isEmpty()){throw new UserNotFoundException();}
        return examResultRepository.findByUUID(examResultId).orElse(null);
    }

    public void addExamResult(UUID ID, UUID ExamID, UUID CorrectorID, UUID StudentID, Integer grade) {
        if (ID.toString().isBlank()|| ExamID.toString().isBlank() ||CorrectorID.toString().isBlank()
                ||StudentID.toString().isBlank() ||grade.toString().isBlank() ){throw new WrongInputException();}

        if (examResultRepository.findByUUID(ID).isPresent()){throw new ExamResultNotFoundException();}

        ExamResult examResult= new ExamResult(ID, ExamID, CorrectorID, StudentID, grade);

        examResultRepository.saveExamResult(examResult);
    }

    public void modifyExamResultScore(Integer examScore, UUID examResultID ) {
        if (examScore.toString().isBlank()|| examResultID.toString().isBlank() ){throw new WrongInputException();}
        if (examResultRepository.findByUUID(examResultID).isEmpty()){throw new UserNotFoundException();}
        examResultRepository.updateExamResultScore(examScore,examResultID);
    }
    public List<ExamResult> findBySubmissionIdAndCorrectorID(UUID SubmissionID, UUID CorrectorID) {

       return examResultRepository.findDistinctBySubmissionIdAndCorrectorID(SubmissionID,CorrectorID);
    }

    public List<ExamResult> findBySubmissionIdAndStudentID(UUID SubmissionID, UUID StudentID) {
        return examResultRepository.findDistinctBySubmissionIdAndStudentID(SubmissionID,StudentID);
    }



    public UserRepository getUserRepository() {
        return userRepository;
    }

}
