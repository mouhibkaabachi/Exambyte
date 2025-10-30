package com.example.propra2proj.controller;

import com.example.propra2proj.applicationlayer.service.ExamResultService;
import com.example.propra2proj.applicationlayer.service.ExamService;
import com.example.propra2proj.applicationlayer.service.SubmissionService;
import com.example.propra2proj.config.SecurityConfig;
import com.example.propra2proj.domain.model.ExamResult;
import com.example.propra2proj.domain.model.Submission;
import com.example.propra2proj.domain.model.examagg.Exam;
import com.example.propra2proj.domain.model.examagg.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import com.example.propra2proj.securityhelper.WithMockOAuth2User;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfig.class)
@WebMvcTest(CorrectorController.class)
public class CorrectorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean(SubmissionService.class)
    SubmissionService submissionService;
    @MockBean(ExamResult.class)
    private ExamResultService examResultService;
    @MockBean(Exam.class)
    private ExamService examService;



    @Test
    @DisplayName("Testing the controller getMapping method of the page Corrector page")
    @WithMockOAuth2User(login = "something", roles = {"Corrector"})
    public void testCorrectorDashboard() throws Exception{
        //Arrange
        UUID userId = UUID.fromString("666666-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        Submission submission1= mock(Submission.class);
        Submission submission2= mock(Submission.class);
        ExamResult examResult1= mock(ExamResult.class);
        ExamResult examResult2=null;

        //Act
        when(submissionService.getCorrectorsSubmissions(userId)).thenReturn(List.of(submission1,submission2));
        when(examResultService.getExamResultsBySubmissionId(submission1.id())).thenReturn(examResult1);
        when(examResultService.getExamResultsBySubmissionId(submission2.id())).thenReturn(examResult2);

        //Assert
        mockMvc.perform(get("/Corrector/Corrector_dashboard/666666"))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(model().attributeExists("correctorId"))
                        .andExpect(model().attribute("name","username"))
                        .andExpect(model().attribute("userId","666666"))
                        .andExpect(model().attributeExists("CorrectedExams"))
                        .andExpect(model().attribute("CorrectedExams",List.of(examResult1)))
                        .andExpect(model().attributeExists("NotCorrectedExams"))
                        .andExpect(model().attribute("NotCorrectedExams",List.of(submission2))) ;
    }


    @Test
    @DisplayName("Testing the controller getMapping method of the page ExamCorrection page")
    @WithMockOAuth2User(login = "something", roles = {"Corrector"})
    public void testExamCorrection()throws Exception{
        //Arrange
        Submission mockedSubmission= mock(Submission.class);
        UUID submissionUUID= UUID.randomUUID();
        UUID questionUUID= UUID.randomUUID();
        Map<UUID,String> StudentMockAnswer= Map.of(questionUUID,"Answer1");
        //Act
        when(submissionService.getSubmissionById(submissionUUID)).thenReturn(mockedSubmission);
        when(mockedSubmission.studentsAnswer()).thenReturn(StudentMockAnswer);
        when(examService.getQuestionsByID(questionUUID)).thenReturn(mock(Question.class));
        //Assert
        mockMvc.perform(get("/Corrector/examCorrection/{submissionId}",submissionUUID))
                .andExpect(status().isOk())
                .andExpect(view().name("/Corrector/examCorrection"))
                .andExpect(model().attribute("answers",StudentMockAnswer))
                .andExpect(model().attribute("questions",List.class))
                .andExpect(model().attribute("submissionId",submissionUUID));
    }

    @Test
    @DisplayName("Testing the controller PostMapping method submit correction page")
    @WithMockOAuth2User(login = "something", roles = {"Corrector"})
    public void testCorrectionModification() throws Exception{
        //Arrange
        Question question1=  mock(Question.class);
        Question question2=  mock(Question.class);
        Submission submission= mock(Submission.class);
        List<Question> mockQuestions=  List.of(question1,question2);
        UUID submissionId= UUID.randomUUID();
        //Act
        when(submissionService.getSubmissionById(submissionId)).thenReturn(submission);
        when(question1.getAccordedPoints()).thenReturn(1);
        when(question2.getAccordedPoints()).thenReturn(2);
        //Assert
        mockMvc.perform(post("/Corrector/submitCorrection").param("submissionID",submissionId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("QuestionWithScores",mockQuestions))
                .andExpect(view().name("/corrector/Corrector_dashboard"));}




}
