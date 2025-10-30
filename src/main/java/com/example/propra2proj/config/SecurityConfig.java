package com.example.propra2proj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {



    @Value("${propra2proj.rollen.corrector}")
    private String correctorRole;

    @Value("${propra2proj.rollen.organiser}")
    private String organiserRole;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/", "/home", "/StyleSheet.css").permitAll()
                        .requestMatchers("/Corrector/corrector_dashboard/**").hasAuthority("ROLE_"+correctorRole)
                        .requestMatchers("/Organiser/organiser_dashboard/**").hasAuthority("ROLE_"+organiserRole)
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/default", true))
                .formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/default", true))
                .logout(logout -> logout.logoutSuccessUrl("/home").permitAll());

        return http.build();
    }
}






