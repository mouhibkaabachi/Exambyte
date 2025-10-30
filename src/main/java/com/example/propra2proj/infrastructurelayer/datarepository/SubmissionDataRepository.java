package com.example.propra2proj.infrastructurelayer.datarepository;

import com.example.propra2proj.applicationlayer.dtos.SubmissionDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubmissionDataRepository extends CrudRepository<SubmissionDTO, Integer> {


    Optional<SubmissionDTO> findBySubmissionId (UUID id);

    List<SubmissionDTO> findAllByStudentID(UUID studentId);

    List<SubmissionDTO> findByExamID (UUID examId);



}
