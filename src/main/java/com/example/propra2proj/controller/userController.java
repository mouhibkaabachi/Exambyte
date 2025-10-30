package com.example.propra2proj.controller;
import com.example.propra2proj.applicationlayer.exceptions.*;
import com.example.propra2proj.applicationlayer.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;


@Controller
public class userController {

    private final UserService userService;

    public userController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home() {return "home";}

    @GetMapping("/default")
    public void defaultAfterLogin(Authentication authentication, HttpServletResponse response) throws Exception {
        String username = authentication.getName();




         if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CORRECTOR"))) {
            if (userService.getUserByUsername(username) != null) {
                 UUID userId = userService.getUserByUsername(username).getId();
                response.sendRedirect("/corrector/Corrector_dashboard/"+userId);}
            else {userService.createNewCorrector(UUID.randomUUID(),username,"" );
                response.sendRedirect("/corrector/Corrector_dashboard/"+userService.getUserByUsername(username).getId());}}
         else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ORGANISER"))) {
            if (userService.getUserByUsername(username) != null) {
            UUID userId = userService.getUserByUsername(username).getId();
            response.sendRedirect("/Organiser/organiser_dashboard/"+userId);}
            else {userService.createNewOrganiser(UUID.randomUUID(),username,"" );
                response.sendRedirect("/Organiser/organiser_dashboard/"+userService.getUserByUsername(username).getId());}
        } else {
             if (userService.getUserByUsername(username) != null) {
                 UUID userID = userService.getUserByUsername(username).getId();
                 response.sendRedirect("/Student/student_dashboard/"+userID);}
             else { userService.createNewStudent(UUID.randomUUID(),username,"" );
                 response.sendRedirect("/Student/student_dashboard/"+userService.getUserByUsername(username).getId());
             }}
    }
    public UserService getUserService() {
        return userService;
    }

    @ExceptionHandler(WrongInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalid() {
        return "ungueltig";
    }

    @ExceptionHandler(ExamResultNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String ExamResultDoesntExist() {
        return "ungueltig";
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String UserAlreadyExists() {
        return "ungueltig";
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String UserNotFound() {
        return "ungueltig";
    }
    @ExceptionHandler(SubmissionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String begriffNichtVorhanden() {
        return "ungueltig";
    }
}
