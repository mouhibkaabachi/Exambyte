package com.example.propra2proj.applicationlayer.dtos;


import jakarta.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Table("ExamResult")
public record ExamResultDTO (


    @Id Integer id, UUID uuid,
    UUID studentID,
    UUID submissionId,
    UUID correctorID,
     Integer grade ) {}



