package com.example.propra2proj.infrastructurelayer.datarepository;


import com.example.propra2proj.applicationlayer.dtos.UserDTO;
import com.example.propra2proj.domain.model.useragg.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends CrudRepository <UserDTO,Integer> {

   List<UserDTO> findAllByRole(Role role);


    Optional<UserDTO> findByUuid(UUID uuid);

    Optional<UserDTO> findByEmail(String email);
    Optional<UserDTO> findByName(String username);




}
