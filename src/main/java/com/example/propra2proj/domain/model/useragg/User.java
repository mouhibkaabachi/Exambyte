package com.example.propra2proj.domain.model.useragg;

import com.example.propra2proj.domain.model.*;

import java.util.UUID;



public class User {
    private final UUID id ;
    private final String name ;
    private final String email ;
    private final Role role ;


    public User(UUID id, String name, String email, Role role) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }



    public static User createOrganiser(UUID id, String name, String email) {
        return new Organiser(id, name, email);
    }

    public static User createCorrector(UUID id, String name, String email) {
        return new Corrector(id, name, email);
    }


    public static User createStudent(UUID id, String name, String email) {
        return new Student(id, name, email);
    }


    public String getDashboard (){
        return null;
    }

}
