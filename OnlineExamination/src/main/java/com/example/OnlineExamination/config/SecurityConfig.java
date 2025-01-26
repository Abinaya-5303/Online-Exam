package com.example.OnlineExamination.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.OnlineExamination.service.UserService;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/login", "/register").permitAll() // Allow public access to login and register pages
                .anyRequest().authenticated() // Restrict access to other pages
            .and()
            .formLogin()
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard upon successful login
                .failureUrl("/login?error=true") // Redirect to login page on failure
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true"); // Redirect to login page after logout

        return http.build();
    }

    @Autowired
    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            var user = userService.findUserByUsername(username);
            if (user == null) throw new RuntimeException("User not found");
            return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        });
    }
}
