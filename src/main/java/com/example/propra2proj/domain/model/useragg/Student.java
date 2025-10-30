package com.example.propra2proj.domain.model.useragg;

import java.util.UUID;

class Student extends User {

     public Student(UUID id, String name, String email) {

        super(id, name, email, Role.ROLE_STUDENT);

    }


    @Override
    public String getDashboard() {
        return "/Student/student_dashboard";
    }



}
