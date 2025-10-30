package com.example.propra2proj.applicationlayer.mapper;

import com.example.propra2proj.applicationlayer.dtos.UserDTO;
import com.example.propra2proj.domain.model.useragg.User;
import org.springframework.stereotype.Component;



public class UserMapper {

   public User toUser(UserDTO userDTO){
       return new User(userDTO.uuid(), userDTO.name(), userDTO.email(),userDTO.role() );
   }
   public UserDTO toUserDTO(User user, Integer id){
       return new UserDTO (id, user.getId(), user.getName(), user.getEmail(), user.getRole());
   }



}
