package com.example.propra2proj.web;

import com.example.propra2proj.Propra2projApplication;
import org.springframework.boot.SpringApplication;

public class ExambyteApplicationWithContainer {


    public static void main(String[] args) {
        SpringApplication.from(Propra2projApplication::main)
                .with(ContainerKonfiguration.class)
                .run(args);
    }
}

