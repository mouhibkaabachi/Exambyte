package com.example.propra2proj.controller;
import com.example.propra2proj.applicationlayer.service.ExamService;
import com.example.propra2proj.applicationlayer.service.UserService;
import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.domain.model.examagg.MultipleChoiceQuestion;
import com.example.propra2proj.domain.model.examagg.Question;
import com.example.propra2proj.domain.model.examagg.TextQuestion;
import com.example.propra2proj.domain.model.useragg.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
public class OrganiserController {
    private final ExamService examService;
    private final UserService userService;

    public OrganiserController(ExamService examService, UserService userService) {
        this.examService = examService;
        this.userService = userService;
    }

    @PostMapping("/Organiser/CreateExams/{userId}")
    public String setupExams(@RequestParam("numberOfTextQuestions") int numberOfTextQuestions, @RequestParam("numberOfMultipleChoiceQuestion") int numberOfMultipleChoiceQuestions,
                             @ModelAttribute Exam exam, @PathVariable UUID userId, RedirectAttributes redirectAttributes) {
       UUID examID= examService.createExam(numberOfMultipleChoiceQuestions
                                ,numberOfTextQuestions,userId,exam.getOverallScore()
                                 ,exam.getName(),exam.getId());


       redirectAttributes.addFlashAttribute("examID", examID);


    return "redirect:/Organiser/ExamForms/"+ userId +examID; }

    @PostMapping("/Organiser/saveExam/{organiserID}")
    public String saveExam(@PathVariable String organiserID, @ModelAttribute("exam") Exam exam,
                            @RequestParam(name = "submitExam", required = false) String submitExam,
                           @RequestParam(name = "saveProgress", required = false) String saveProgress){
        if (Objects.equals(submitExam, "true")){
        examService.setExamToCompleted(exam.getId());
        examService.save(exam);}
        else if (Objects.equals(saveProgress, "true")){
          examService.save(exam);
        }
        return "redirect:Organiser/OrganiserDashboard" ;}

    @GetMapping("/Organiser/CreateExams/{userId}")
    public String showCreateExamsForm(@PathVariable UUID userId, Model model) {
        Exam exam = new Exam(UUID.randomUUID(), "", 0
                , new ArrayList<>(), userId
                , new ArrayList<>(), false);

        model.addAttribute("exam", exam);
        model.addAttribute("userId", userId);
        return "Organiser/CreateExams";
    }

    @GetMapping("/Organiser/ExamForms/{UserId}/{examID}")
    public String showExamForms(Model model, @PathVariable UUID UserId, @PathVariable UUID examID) {

        Exam exam= examService.FindExam(examID);
        model.addAttribute("user",userService.getUserById(UserId));
        model.addAttribute("exam",exam);
        return "Organiser/ExamForms";}

    @GetMapping("/Organiser/OrganiserDashboard/{UserId}")
    @Secured("ROLE_ORGANISER")
    public String OrganiserDashboard(@PathVariable UUID UserId, Model model) {
        User user = userService.getUserById(UserId);
        List<Exam> SubmittedExams= examService.findAllExamsByOrganiserID(UserId)
                                              .stream().filter(Exam::isCompleted).toList();

        List<Exam> IncompleteExams= examService.findAllExamsByOrganiserID(UserId)
                                               .stream().filter(x-> !x.isCompleted()).toList();

        model.addAttribute("name", user.getName());
        model.addAttribute("organiserID",UserId);
        model.addAttribute("examsInProgress", IncompleteExams);
        model.addAttribute("submittedExams", SubmittedExams);
        return "Organiser/OrganiserDashboard";
    }
    @GetMapping("/Organiser/ModifyExams/{examID}/{OrganiserId}")
    public String showModifyExamPage(@PathVariable UUID examID, Model model
                                     ,@PathVariable String OrganiserId) {

        Exam exam = examService.FindExam(examID);

        List<MultipleChoiceQuestion> MCQuestions= new ArrayList<>();

        List<TextQuestion> TextQuestions= new ArrayList<>();
        for (Question question: exam.getQuestions()){
            if (question instanceof MultipleChoiceQuestion){
                MCQuestions.add((MultipleChoiceQuestion) question);
            }
            else if (question instanceof TextQuestion){
                     TextQuestions.add((TextQuestion) question);
            }
        }
        model.addAttribute("mcQuestions",MCQuestions);
        model.addAttribute("textQuestions", TextQuestions);
        model.addAttribute("exam", exam);
        model.addAttribute("userId", OrganiserId);

        return "Organiser/ModifyExams";
    }
    @PostMapping("/Organiser/saveModifiedExam/{organiserID}")
    public String saveModifiedExam(@PathVariable UUID organiserID, @ModelAttribute("exam") Exam exam,
                                   @RequestParam(name = "submitExam", required = false) String submitExam) {

        if ("true".equals(submitExam)) {
            examService.setExamToCompleted(exam.getId());
        }
        examService.save(exam);
        return "redirect:/Organiser/OrganiserDashboard/" + organiserID;
    }




}
