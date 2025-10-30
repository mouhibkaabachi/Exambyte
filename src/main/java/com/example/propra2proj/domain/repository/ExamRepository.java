package com.example.propra2proj.domain.repository;

import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.domain.model.examagg.Question;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExamRepository  {


    Collection<Exam> findAll();

    Optional<Exam> findByUUID(UUID id );

    List<Exam> findByOrganiserID(UUID id);


    List<Exam> findByStudentID(UUID StudentID);

    void save(Exam exam );


    void ExamRegistry(UUID examID, UUID newStudentID);

    void DeleteStudentFromExam(UUID examID, UUID StudentID);

    Optional<Question> findQuestionById(UUID id);

    void saveQuestion(UUID examID ,Question question);

    void deleteQuestion(UUID examID,Question question);

    List<Question> findAllQuestions(UUID ExamID);







}

