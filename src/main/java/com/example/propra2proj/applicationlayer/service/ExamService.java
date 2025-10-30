package com.example.propra2proj.applicationlayer.service;


import com.example.propra2proj.applicationlayer.exceptions.ExamNotFoundException;
import com.example.propra2proj.applicationlayer.exceptions.UserAlreadyExistsException;
import com.example.propra2proj.applicationlayer.exceptions.WrongInputException;

import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.domain.model.examagg.MultipleChoiceQuestion;
import com.example.propra2proj.domain.model.examagg.Question;
import com.example.propra2proj.domain.model.examagg.TextQuestion;
import com.example.propra2proj.domain.repository.ExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExamService{

    private final ExamRepository examRepository;



    public ExamService(ExamRepository examRepository)
    {
        this.examRepository = examRepository;

    }


    public Exam FindExam(UUID id) {
        if (id.toString().isBlank() || id.toString().isEmpty()) {
            throw  new WrongInputException();
        }

        return examRepository.findByUUID(id).orElseThrow(ExamNotFoundException::new);
    }


    @Transactional
    public void updateAssignedStudents(UUID StudentID, UUID ExamID) {
        if (ExamID.toString().isBlank() || ExamID.toString().isEmpty() || StudentID.toString().isBlank() || StudentID.toString().isEmpty()) {
            throw  new WrongInputException();
        }
        if (examRepository.findByUUID(ExamID).isEmpty()){ throw new ExamNotFoundException();}
        if (examRepository.findByUUID(ExamID).get().getAssignedStudentsId().stream().anyMatch(x->x==StudentID)){
            throw new  UserAlreadyExistsException() ;
        }
        examRepository.ExamRegistry(StudentID,ExamID);
    }

    @Transactional
    public void ExamRegistry(UUID StudentID, UUID ExamID){
        examRepository.ExamRegistry(ExamID,StudentID);
        examRepository.save(examRepository.findByUUID(ExamID).orElseThrow());
    }

    public List<Exam> findAllExamsByOrganiserID(UUID organiserID){
        return examRepository.findByOrganiserID(organiserID);
    }
    public void setExamToCompleted(UUID ExamID){

       Exam exam= examRepository.findByUUID(ExamID).get();
       examRepository.save(new Exam(exam.getId(),exam.getName(),
               exam.getOverallScore(),exam.getQuestions(),exam.getOrganiserID(),
               exam.getAssignedStudentsId(),true));
    }
    public List<Exam> findAllByStudentId(UUID StudentId){
        if (StudentId.toString().isEmpty()){throw new WrongInputException ();}
        return examRepository.findByStudentID(StudentId);
    }




    @Transactional
    public void addQuestion(UUID ExamID, Question question){
        if (examRepository.findByUUID(ExamID).isEmpty()){ throw new ExamNotFoundException();}
        List<Question> questionList= examRepository.findByUUID(ExamID).get().getQuestions();
        questionList.add(question);
    }
    @Transactional
    public UUID createExam(int numberOfMultipleChoiceQuestion,
                           int numberOfTextQuestion, UUID OrganiserID, int score, String name, UUID ExamID){
        List<Question> listOfQuestions = new ArrayList<Question>(numberOfTextQuestion+numberOfMultipleChoiceQuestion);
        Exam exam= new Exam(ExamID,name,score,listOfQuestions,OrganiserID,new ArrayList<>(),false);

        examRepository.save(exam);

        return exam.getId();}

    @Transactional
    public void AddQuestions(UUID ExamID, List<Question> questionList){
        if (examRepository.findByUUID(ExamID).isEmpty()){ throw new ExamNotFoundException();}
        Exam exam = examRepository.findByUUID(ExamID).get();
        examRepository.save(new Exam(exam.getId(),exam.getName(),exam.getOverallScore()
                ,questionList,exam.getOrganiserID(),exam.getAssignedStudentsId(),exam.isCompleted() ));
    }

    public Integer getNumberOfTextQuestions(UUID ExamID){
        Exam exam= this.FindExam(ExamID);
     return  exam.getQuestions().stream().filter(x->x instanceof TextQuestion).toList().size() ;}

    public Integer getNumberOfMCQuestion(UUID ExamID){
        Exam exam= this.FindExam(ExamID);
        return  exam.getQuestions().stream().filter(x->x instanceof MultipleChoiceQuestion).toList().size() ;}

    public void save(Exam exam){
        examRepository.save(exam);
    }


    @Transactional
    public void modifyQuestion(UUID ExamID, UUID QuestionId, Question Updatedquestion) {
       if (examRepository.findByUUID(ExamID).isEmpty()){ throw new ExamNotFoundException();}
        if (examRepository.findQuestionById(QuestionId).isEmpty()){ throw new ExamNotFoundException();}

       Exam exam = examRepository.findByUUID(ExamID).get();
       examRepository.deleteQuestion(ExamID, examRepository.findQuestionById(QuestionId).get());
       exam.getQuestions().add(Updatedquestion);
       examRepository.save(exam);
    }

    public Question getQuestionsByID(UUID questionID){
        if (examRepository.findQuestionById(questionID).isEmpty()){ throw new ExamNotFoundException();}
        return examRepository.findQuestionById(questionID).get();
    }
    public void updateQuestionAccordedPoints(UUID examID,UUID questionID, Integer AccordedPoint){
        if (examRepository.findQuestionById(questionID).isEmpty()){ throw new ExamNotFoundException();}
        Question question = examRepository.findQuestionById(questionID).get();
        Question question1= new Question(question.getId(),question.getQuestionText(),AccordedPoint,question.getPoints());
        examRepository.saveQuestion(examID,question1);
    }
    



    }


