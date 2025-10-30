package com.example.propra2proj.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(OrganiserController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrganiserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ORGANISER")
    public void testStep1() throws Exception {
        int numberOfTextQuestions = 4;
        int numberOfMultipleChoiceQuestions = 4;

        mockMvc.perform(post("/Organiser/step1")
                        .param("numberOfTextQuestions", String.valueOf(numberOfTextQuestions))
                        .param("numberOfMultipleChoiceQuestions",
                                String.valueOf(numberOfMultipleChoiceQuestions)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("Organiser/exam_forms"));
    }


    @Test
    public void testshowExamForm() throws Exception {
        mockMvc.perform(get("/Organiser/exam_forms"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Organiser/exam_forms"))
                .andExpect(model().attributeExists("numberOfTextQuestions"))
                .andExpect(model().attributeExists("numberOfMultipleChoiceQuestions"));
    }


    @Test
    public void testshowCreateExamsForm() throws Exception {
        mockMvc.perform(get("/Organiser/create_exams"))
                .andExpect(status().isOk())
                .andExpect(view().name("/Organiser/create_exams"));
    }

    @Test
    void testSaveExam() throws Exception {
        mockMvc.perform(post("/saveExam"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Organiser/organiser_dashboard"));
    }


}