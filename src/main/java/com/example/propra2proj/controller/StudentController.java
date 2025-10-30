package com.example.propra2proj.controller;

import com.example.propra2proj.applicationlayer.service.ExamResultService;
import com.example.propra2proj.applicationlayer.service.ExamService;
import com.example.propra2proj.applicationlayer.service.SubmissionService;
import com.example.propra2proj.applicationlayer.service.UserService;
import com.example.propra2proj.domain.model.ExamResult;
import com.example.propra2proj.domain.model.Submission;
import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.domain.model.examagg.MultipleChoiceQuestion;
import com.example.propra2proj.domain.model.examagg.Question;
import com.example.propra2proj.domain.model.examagg.TextQuestion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller

public class StudentController {
    private final UserService userService;
    private final ExamService examService;
    private final SubmissionService submissionService;
    private final ExamResultService examResultService;


    public StudentController(UserService userService, ExamService examService , SubmissionService submissionService, ExamResultService examResultService) {
        this.userService = userService;
        this.examService = examService;
        this.submissionService = submissionService;
        this.examResultService = examResultService;
    }

    @GetMapping("/StudentDashboard/{studentId}")
    public String studentDashboard(Model model, @PathVariable UUID studentId) {

        String studentName =userService.getUserById(studentId).getName();
        List<Exam> exams = examService.findAllByStudentId(studentId);

        model.addAttribute("studentId", studentId);
        model.addAttribute("name", studentName);
        model.addAttribute("exams", exams);


        return "Student/StudentDashboard";
    }

    @GetMapping("/ExamSubmission/{studentId}/{examId}")
    public String showExamForm(@PathVariable UUID examId, Model model, @PathVariable UUID studentId) {
        Exam exam = examService.FindExam(examId);
        examService.ExamRegistry(examId,studentId);

        List<MultipleChoiceQuestion> multipleChoiceQuestions = new ArrayList<>() ;
        List<TextQuestion> textQuestions = new ArrayList<>() ;
        for(Question question : exam.getQuestions()) {
            if(question instanceof MultipleChoiceQuestion) {
                multipleChoiceQuestions.add((MultipleChoiceQuestion) question);
            }
            if(question instanceof TextQuestion) {
                textQuestions.add((TextQuestion) question);
            }
        }
        model.addAttribute("multipleChoiceQuestions", multipleChoiceQuestions);
        model.addAttribute("textQuestions", textQuestions);
        model.addAttribute("exam", exam);

        return "Student/ExamSubmission";
    }
    @PostMapping("/submitExam/{studentId}/{examId}")
    public String submitExam(
            @RequestParam Map<UUID, String> mcAnswers,
            @RequestParam Map<UUID, String> studentsAnswer,
            @PathVariable UUID examId, @PathVariable UUID studentId) {

        Map<UUID, String> allAnswers = new HashMap<>();
        allAnswers.putAll(mcAnswers);
        allAnswers.putAll(studentsAnswer);
        Submission submission = new Submission(UUID.randomUUID(), examId, studentId,
                allAnswers, userService.assignARandomCorrector());
        submissionService.saveSubmission(submission);

        return "Student/StudentDashboard";
    }






    @GetMapping("/ExamResults/{studentId}")
    public String studentExam(Model model, @PathVariable UUID studentId) {
        List<ExamResult> examResults =examResultService.getExamResultByStudentId(studentId);
        model.addAttribute("ExamResult",examResults);
        return "Student/ExamResults" ;

    }


}

