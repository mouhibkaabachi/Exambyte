package com.example.propra2proj.infrastructurelayer.datarepository;

import com.example.propra2proj.applicationlayer.dtos.examagg.ExamDTO;

import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ExamDataRepository extends CrudRepository<ExamDTO, Integer> {

       List<ExamDTO> findAll();
       Optional<ExamDTO> findByUuid(UUID id);


       List<ExamDTO> findByOrganiserID( UUID organiserID);




























   }