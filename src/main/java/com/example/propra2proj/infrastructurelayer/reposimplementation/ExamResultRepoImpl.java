package com.example.propra2proj.infrastructurelayer.reposimplementation;
import com.example.propra2proj.applicationlayer.dtos.ExamResultDTO;
import com.example.propra2proj.infrastructurelayer.datarepository.ExamResultDataRepository;
import com.example.propra2proj.applicationlayer.exceptions.ExamResultNotFoundException;
import com.example.propra2proj.domain.model.ExamResult;
import com.example.propra2proj.domain.repository.ExamResultRepository;
import com.example.propra2proj.applicationlayer.mapper.ExamResultMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ExamResultRepoImpl implements ExamResultRepository {
    private final ExamResultDataRepository examResultDataRepository;
    private final ExamResultMapper examResultMapper = new ExamResultMapper();

    public ExamResultRepoImpl(ExamResultDataRepository examResultDataRepository) {
        this.examResultDataRepository = examResultDataRepository;

    }

    public ExamResultDataRepository getExamresultDataRepository() {
        return examResultDataRepository;
    }

    public ExamResultMapper getExamResultMapper() {
        return examResultMapper;
    }


    @Override
    public Optional<ExamResult> findByUUID(UUID id) {
        return examResultDataRepository.findByUuid(id).map(examResultMapper::toExamResult);
    }

    @Override
    public ExamResult findBySubmissionId( UUID submissionId) {
        return examResultMapper.toExamResult(examResultDataRepository.findBySubmissionId(submissionId));
    }

    @Override
    public List<ExamResult> findAllByCorrectorID( UUID correctorID) {
        return examResultDataRepository.findAllByCorrectorID(correctorID).stream().map(examResultMapper::toExamResult).toList();
    }

    @Override
    public List<ExamResult> findAllByStudentID( UUID StudentID) {
        return examResultDataRepository.findAllByStudentID(StudentID).stream().map(examResultMapper::toExamResult).toList();
    }

    @Override
    public List<ExamResult> findDistinctBySubmissionIdAndStudentID( UUID submissionId, UUID studentID) {
        return examResultDataRepository.findDistinctBySubmissionIdAndStudentID(submissionId, studentID)
                .stream().map(examResultMapper::toExamResult).toList();
    }

    @Override
    public List<ExamResult> findDistinctBySubmissionIdAndCorrectorID( UUID submissionId,  UUID correctorID) {
        return examResultDataRepository
                .findDistinctBySubmissionIdAndCorrectorID(submissionId,correctorID)
                .stream().map(examResultMapper::toExamResult).toList();
    }

    @Override
    public void updateExamResultScore(Integer Score, UUID ExamResultID) {
        Integer DBKey=  examResultDataRepository.findByUuid(ExamResultID).map(ExamResultDTO::id).orElse(null);
        ExamResultDTO resultDTO= examResultDataRepository.findByUuid(ExamResultID).orElseThrow(ExamResultNotFoundException::new);

        examResultDataRepository.save(new ExamResultDTO(DBKey,resultDTO.uuid(),resultDTO.studentID(),
                resultDTO.submissionId(),resultDTO.correctorID(),Score));
    }


    @Override
    public void saveExamResult(ExamResult examResult) {
        Integer DBKey= examResultDataRepository.findByUuid(examResult.getID()).map(ExamResultDTO::id).orElse(null);
     examResultDataRepository.save(examResultMapper.toExamResultDTO(examResult,DBKey));
    }
}
