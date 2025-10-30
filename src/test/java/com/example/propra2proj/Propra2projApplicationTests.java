package com.example.propra2proj;

import com.example.propra2proj.web.ContainerKonfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(ContainerKonfiguration.class)
class Propra2projApplicationTests {

    @Test

    void contextLoads() {
    }

}
