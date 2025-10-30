package com.example.propra2proj.applicationlayer.dtos;

import com.example.propra2proj.domain.model.useragg.Role;
import jakarta.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Table("Users")
public record UserDTO (
    @Id
    Integer id,
    UUID uuid,
    String name,
    String email,
    Role role ) {}




