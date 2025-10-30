package com.example.propra2proj.applicationlayer.dtos;

import jakarta.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Table("Submission")
public record SubmissionDTO(@Id Integer id, UUID submissionId, UUID examID, UUID studentID, Map<UUID,String> studentsAnswer, UUID correctorID) {
}
