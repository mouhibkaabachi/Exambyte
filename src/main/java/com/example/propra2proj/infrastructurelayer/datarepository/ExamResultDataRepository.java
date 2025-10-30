package com.example.propra2proj.infrastructurelayer.datarepository;

import com.example.propra2proj.applicationlayer.dtos.ExamResultDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamResultDataRepository extends CrudRepository<ExamResultDTO, Integer> {

     Optional<ExamResultDTO> findByUuid(UUID id);

     ExamResultDTO findBySubmissionId( UUID submissionId);

     List<ExamResultDTO> findAllByCorrectorID(UUID correctorID);

     List<ExamResultDTO> findAllByStudentID( UUID studentID);

     List<ExamResultDTO>findDistinctBySubmissionIdAndStudentID( UUID ExamId,  UUID studentID);

     List<ExamResultDTO> findDistinctBySubmissionIdAndCorrectorID( UUID examId,  UUID correctorID);












}
