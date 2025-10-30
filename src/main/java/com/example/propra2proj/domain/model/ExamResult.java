package com.example.propra2proj.domain.model;

import java.util.UUID;


public class ExamResult {

    private final UUID ID;
    private final UUID StudentID;
    private final UUID SubmissionID;
    private final UUID CorrectorID;
    private final int grade;


    public ExamResult(UUID ID, UUID SubmissionID, UUID CorrectorID, UUID StudentID, int grade) {
        this.ID = ID;
        this.StudentID = StudentID;
        this.SubmissionID = SubmissionID;
        this.CorrectorID = CorrectorID;
        this.grade = grade;
    }
    public UUID getID() {
        return this.ID;
    }

    public UUID getStudentID() {
        return this.StudentID;
    }

    public UUID getSubmissionID() {
        return this.SubmissionID;
    }

    public UUID getCorrectorID() {
        return this.CorrectorID;
    }

    public int getGrade() {
        return this.grade;
    }




}
