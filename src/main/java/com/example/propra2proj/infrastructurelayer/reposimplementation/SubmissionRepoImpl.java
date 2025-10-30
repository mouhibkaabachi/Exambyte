package com.example.propra2proj.infrastructurelayer.reposimplementation;

import com.example.propra2proj.infrastructurelayer.datarepository.SubmissionDataRepository;
import com.example.propra2proj.applicationlayer.dtos.SubmissionDTO;
import com.example.propra2proj.domain.model.Submission;
import com.example.propra2proj.domain.repository.SubmissionRepository;
import com.example.propra2proj.applicationlayer.mapper.SubmissionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SubmissionRepoImpl implements SubmissionRepository {
    private final SubmissionDataRepository submissionDataRepository;
    private final SubmissionMapper submissionMapper = new SubmissionMapper();

    public SubmissionRepoImpl(SubmissionDataRepository submissionDataRepository) {
        this.submissionDataRepository = submissionDataRepository;

    }



    @Override
    public void submit(Submission submission) {
        Integer DBkey = submissionDataRepository.findBySubmissionId(submission.id()).orElse(null).id();
        submissionDataRepository.save(submissionMapper.toSubmissionDTO(submission, DBkey));
    }

    @Override
    public Optional<Submission> getSubmissionById(UUID id) {
      return  submissionDataRepository.findBySubmissionId(id).stream().map(this.submissionMapper::toSubmission).findAny();
    }

    @Override
    public List<Submission> getAllSubmissionsByStudentId(UUID studentId) {

        return submissionDataRepository.findAllByStudentID(studentId).stream().map(this.submissionMapper::toSubmission).toList();
    }

    @Override
    public List<Submission> getAllSubmissionsByExamId(UUID examId) {

        return submissionDataRepository.findAllByStudentID(examId).stream().map(this.submissionMapper::toSubmission).toList();
    }

    @Override
    public Map<UUID, String> getAnswersBySubmissionId(UUID submissionId) {

        return submissionDataRepository.findBySubmissionId(submissionId).get().studentsAnswer();
    }

    @Override
    public List<Submission> getSubmissionsByExamId( UUID examId) {
        return submissionDataRepository.findByExamID(examId).stream().map(this.submissionMapper::toSubmission).toList();
    }

    @Override
    public List<Submission> getSubmissionByCorrectorID(UUID correctorId) {
        return List.of();
    }

    @Override
    public void updateExamCorrectorID(UUID SubmissionID, UUID newCorrectorID) {
        Integer DBKey=  submissionDataRepository.findBySubmissionId(SubmissionID).map(SubmissionDTO::id).orElse(null);
        SubmissionDTO Submission= submissionDataRepository.findBySubmissionId(SubmissionID).orElse(null);

        submissionDataRepository.save(new SubmissionDTO(DBKey,Submission.submissionId(),Submission.examID(),Submission.studentID(),Submission.studentsAnswer(),newCorrectorID))  ;
    }


    public SubmissionDataRepository getSubmissionDataRepository() {
        return submissionDataRepository;
    }
}
