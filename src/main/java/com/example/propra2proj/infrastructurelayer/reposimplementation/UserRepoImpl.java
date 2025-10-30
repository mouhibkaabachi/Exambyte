package com.example.propra2proj.infrastructurelayer.reposimplementation;

import com.example.propra2proj.applicationlayer.dtos.UserDTO;
import com.example.propra2proj.infrastructurelayer.datarepository.UserDataRepository;
import com.example.propra2proj.domain.model.useragg.Role;
import com.example.propra2proj.domain.model.useragg.User;
import com.example.propra2proj.domain.repository.UserRepository;
import com.example.propra2proj.applicationlayer.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserRepoImpl implements UserRepository {

private final UserDataRepository userDataRepository;
private final UserMapper userMapper = new UserMapper();

    public UserRepoImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;

    }

    public UserDataRepository getUserDataRepository() {
        return userDataRepository;
    }
    public UserMapper getUserMapper() {
        return userMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDataRepository.findByEmail(email).stream().map(this.userMapper::toUser).findAny();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDataRepository.findByName(username).stream().map(this.userMapper::toUser).findAny();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDataRepository.findByUuid(id).stream().map(this.userMapper::toUser).findAny();
    }

    @Override
    public List<User> findByRole(Role role) {
        return userDataRepository.findAllByRole(role).stream().map(this.userMapper::toUser).collect(Collectors.toList());
    }

    @Override
    public void save(User user) {
        Integer DBKey= userDataRepository.findByUuid(user.getId()).map(UserDTO::id).orElse(null);
        userDataRepository.save(userMapper.toUserDTO(user,DBKey));
    }








}
