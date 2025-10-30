package com.example.propra2proj.controller;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrganiserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHomePageRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(redirectedUrl("/home"));

    }

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void testOldExams() throws Exception {
        mockMvc.perform(get("/student/old_exams"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/old_exams"));
    }

    @Test
    void testAvailableExams() throws Exception {
        mockMvc.perform(get("/student/available_exams"))
                .andExpect(status().isOk())
                .andExpect(view().name("/student/available_exams"));
    }

    @Test
    public void testStudentDashboardNoAuth() throws Exception {

//        MockHttpSession session = AuthenticationTemplate.studentSession();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("testUser");

        mockMvc.perform(get("/student/student_dashboard")
                        .principal(mockPrincipal))
//                        .session(session))
                .andExpect(status().isOk())
//                .andExpect(model().attribute("name", "Sebastian Student"))
                .andExpect(model().attribute("name", "testUser"))
                .andExpect(view().name("/student/student_dashboard"));
    }

    @Test
    public void testCorrectorDashboardNoAuth() throws Exception {

//        MockHttpSession session = AuthenticationTemplate.correctorSession();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("testUser");

        mockMvc.perform(get("/corrector/corrector_dashboard")
                        .principal(mockPrincipal))
//                        .session(session))
                .andExpect(status().isOk())
//                .andExpect(model().attribute("name", "Tony Corrector"))
                .andExpect(model().attribute("name", "testUser"))
                .andExpect(view().name("/corrector/corrector_dashboard"));
    }

    @Test
    public void testOrganiserDashboardNoAuth() throws Exception {

//        MockHttpSession session = AuthenticationTemplate.organiserSession();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("testUser");

        mockMvc.perform(get("/organiser/organiser_dashboard")
                        .principal(mockPrincipal))
//                        .session(session))
                .andExpect(status().isOk())
//                .andExpect(model().attribute("name", "Otto Organisator"))
                .andExpect(model().attribute("name", "testUser"))
                .andExpect(view().name("/organiser/organiser_dashboard"));
    }


}