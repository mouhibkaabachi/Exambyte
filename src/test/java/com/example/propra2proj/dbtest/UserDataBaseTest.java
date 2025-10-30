package com.example.propra2proj.dbtest;


import com.example.propra2proj.infrastructurelayer.datarepository.UserDataRepository;
import com.example.propra2proj.domain.model.useragg.Role;
import com.example.propra2proj.domain.model.useragg.User;
import com.example.propra2proj.domain.repository.UserRepository;
import com.example.propra2proj.infrastructurelayer.reposimplementation.UserRepoImpl;
import com.example.propra2proj.web.ContainerKonfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ContainerKonfiguration.class)
public class UserDataBaseTest {
    @Autowired
    private UserDataRepository userDataRepository;

    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        userRepository = new UserRepoImpl(userDataRepository);
    }

    @Test
    @DisplayName("Testing if we can load and save a user")
    public void testSaveAndLoad() throws Exception{
        User user= new User(UUID.randomUUID(),"mockito","mockitoEmail", Role.ROLE_CORRECTOR);
        userRepository.save(user);
        Optional<User> user1= userRepository.findById(user.getId());
        assertThat(user1.map(User::getName).orElseThrow()).isEqualTo(user.getName());
        assertThat(user1.map(User::getEmail).orElseThrow()).isEqualTo(user.getEmail());
    }


}
