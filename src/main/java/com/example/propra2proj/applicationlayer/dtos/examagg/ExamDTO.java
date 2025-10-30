package com.example.propra2proj.applicationlayer.dtos.examagg;


import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;
import java.util.List;
import java.util.UUID;


@Table("Exam")
public record ExamDTO (
        @Id
    Integer id,
    String name,
    UUID uuid,
    Integer overallScore,
    List<QuestionDTO> questions,
      UUID organiserID,
    List<UUID> AssignedStudentsId,
         boolean isCompleted ){}












