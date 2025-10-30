package com.example.propra2proj.domain.model.useragg;


import java.util.UUID;

class Corrector extends User {


    public Corrector(UUID id, String name, String email) {

        super(id, name, email, Role.ROLE_CORRECTOR);

    }


    @Override
    public String getDashboard() {
        return "/Corrector/Corrector_Dashboard";
    }


}


