package com.example.propra2proj.applicationlayer.service;

import com.example.propra2proj.applicationlayer.exceptions.UserAlreadyExistsException;
import com.example.propra2proj.applicationlayer.exceptions.WrongInputException;
import com.example.propra2proj.domain.model.useragg.Role;
import com.example.propra2proj.domain.model.useragg.User;
import com.example.propra2proj.applicationlayer.exceptions.UserNotFoundException;
import com.example.propra2proj.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }




    public void createNewOrganiser(UUID id, String name, String email){
        if (email.isEmpty() || email.isBlank()  || id.toString().isBlank() || name.isEmpty() || name.isBlank()){
            throw new WrongInputException();
        }
        if (userRepository.findById(id).isPresent()){ throw new UserAlreadyExistsException(); }


        User organiser= User.createOrganiser(id,name,email);
        userRepository.save(organiser);
    }

    public void createNewCorrector(UUID id, String name, String email){

        if (email.isEmpty() || email.isBlank()  || id.toString().isBlank() || name.isEmpty() || name.isBlank()){
            throw new WrongInputException();
        }
        if (userRepository.findById(id).isPresent()){ throw new UserAlreadyExistsException(); }


        User Corrector= User.createCorrector(id,name,email);
        userRepository.save(Corrector);
    }

    public void createNewStudent(UUID id, String name, String email){

        if (email.isEmpty() || email.isBlank()  || id.toString().isBlank() || name.isEmpty() || name.isBlank()){
            throw new WrongInputException();
        }
        if (userRepository.findById(id).isPresent()){ throw new UserAlreadyExistsException(); }


        User Student = User.createStudent(id,name,email);
        userRepository.save(Student);
    }

    public User getUserById(UUID Id){
        if (Id.toString().isBlank()){throw new WrongInputException();}
        return userRepository.findById(Id).orElseThrow(UserNotFoundException::new);
    }
    public UUID assignARandomCorrector(){
        List<User> users= userRepository.findByRole(Role.ROLE_CORRECTOR);
        Random random= new Random();
        int IndexOfCorrector= random.nextInt(users.size());
        return users.get(IndexOfCorrector).getId();
    }

    public User getUserByEmail(String email){
        if (email.isBlank() || email.isEmpty()){throw new WrongInputException();}

        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByUsername(String username){
        if (username.isBlank() || username.isEmpty()){throw new WrongInputException();}
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByRoleAndId(Role role, UUID id){
        if (id.toString().isBlank()){throw new WrongInputException();}
        return userRepository.findByRole(role).stream()
                .filter(user -> user.getId().equals(id)).findFirst()
                .orElseThrow(UserNotFoundException::new);
    }

}
