package com.example.propra2proj.domain.model.useragg;


import java.util.UUID;

 class Organiser extends User {


     Organiser(UUID id, String name, String email) {

        super(id, name, email, Role.ROLE_ORGANIZER);

    }

    @Override
    public String getDashboard() {
        return "/Organiser/Organiser_Dashboard";
    }












}
