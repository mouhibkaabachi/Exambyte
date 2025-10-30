package com.example.propra2proj.domain.repository;

import com.example.propra2proj.domain.model.useragg.Role;
import com.example.propra2proj.domain.model.useragg.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findById(UUID id);

    List <User> findByRole(Role role);

    void save(User user);









}
