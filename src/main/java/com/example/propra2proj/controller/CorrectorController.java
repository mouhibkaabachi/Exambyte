package com.example.propra2proj.controller;
import com.example.propra2proj.applicationlayer.service.ExamResultService;
import com.example.propra2proj.applicationlayer.service.ExamService;
import com.example.propra2proj.applicationlayer.service.SubmissionService;
import com.example.propra2proj.domain.model.ExamResult;
import com.example.propra2proj.domain.model.Submission;
import com.example.propra2proj.domain.model.examagg.Question;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
public class CorrectorController {
    private final SubmissionService submissionService;
    private final ExamResultService examResultService;
    private final ExamService examService;

    public CorrectorController(SubmissionService submissionService
            ,ExamResultService examResultService, ExamService examService) {
        this.submissionService = submissionService;

        this.examResultService = examResultService;
        this.examService = examService;
    }

    @GetMapping("/corrector/CorrectorDashboard/{userId}")
    @Secured("ROLE_CORRECTOR")
    public String CorrectorDashboard(OAuth2AuthenticationToken auth, Model model, @PathVariable UUID userId) {
        model.addAttribute("name", auth.getName());
        model.addAttribute("userId", userId);


        List<Submission> CorrectedExams = new ArrayList<>();
        List<Submission> NotCorrectedExams = new ArrayList<>();
        for(Submission submission: submissionService.getCorrectorsSubmissions(userId)) {
            ExamResult examResult= examResultService.getExamResultsBySubmissionId(submission.id());
            if(examResult != null) {
                CorrectedExams.add(submission);
            }
            else {
                NotCorrectedExams.add(submission);}
        }
        model.addAttribute("CorrectedExams", CorrectedExams);
        model.addAttribute("NotCorrectedExams", NotCorrectedExams);
        return "corrector/CorrectorDashboard";
    }


    @GetMapping("/Corrector/ExamCorrection/{submissionId}")
    @Secured("ROLE_CORRECTOR")
    public String CorrectExams(Model model, @PathVariable UUID submissionId) {
       List<Question> questions= submissionService.getSubmissionById(submissionId)
               .studentsAnswer().keySet().stream()
               .map(examService::getQuestionsByID).toList();

       model.addAttribute("answers", submissionService.getSubmissionById(submissionId).studentsAnswer());
       model.addAttribute("questions", questions);
       model.addAttribute("submissionId", submissionId);
        return "corrector/ExamCorrection";
    }

    @PostMapping("/submitCorrections")
    @Secured("ROLE_CORRECTOR")
    public String submitCorrections(@ModelAttribute List<Question> QuestionsWithScores, @RequestParam("submissionId") UUID submissionId) {
    Submission submission = submissionService.getSubmissionById(submissionId);
    int grade=0;
    for (Question question: QuestionsWithScores){
        grade+=question.getAccordedPoints();}
    examResultService.createExamResult(submission.studentID(),submission.id(),submission.correctorID(),grade);

   return "redirect:/corrector/Corrector_dashboard" ;}

    @GetMapping("/corrector/CorrectionModification/{submissionId}")
    @Secured("ROLE_CORRECTOR")
    public String showPageOfModification(Model model,@PathVariable UUID submissionId){
        Submission submission= submissionService.getSubmissionById(submissionId);
        List<Question> questions= submissionService.getSubmissionById(submissionId)
                .studentsAnswer().keySet().stream()
                .map(examService::getQuestionsByID).toList();

        model.addAttribute("submission", submission);

        model.addAttribute("questions",questions);

        model.addAttribute("answers",
                submissionService.getSubmissionById(submissionId).studentsAnswer());

    return "corrector/CorrectionModification" ;}

    @PostMapping("/SubmissionModification")
    @Secured("ROLE_CORRECTOR")
    public String submitNewModification(@RequestParam Map<UUID,Integer> accordedPoints, @ModelAttribute Submission submission){
        for (UUID questionID: accordedPoints.keySet() ){
        examService.updateQuestionAccordedPoints(submission.examID(),questionID,accordedPoints.get(questionID));}

        return "redirect:/corrector/Corrector_dashboard" ;}

}
