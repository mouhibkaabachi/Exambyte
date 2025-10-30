package com.example.propra2proj.domain.model;

import java.util.Map;
import java.util.UUID;

public record Submission(UUID id, UUID examID, UUID studentID, Map<UUID, String> studentsAnswer, UUID correctorID) {

}


