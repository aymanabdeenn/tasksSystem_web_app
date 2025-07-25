package com.example.tasksSystem.security;

import com.example.tasksSystem.Services.ManagerService;
import com.example.tasksSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;
    private final ManagerService managerService;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(UserService userService , ManagerService managerService , CustomUserDetailsService customUserDetailsService){
        this.userService = userService;
        this.managerService = managerService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(customUserDetailsService)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/styles/styleAuthForms/**" , "/scripts/**" , "/images/**", "/authForms/**").permitAll()
                        .requestMatchers("/user/**" , "/styles/styleUser/**").hasRole("USER")
                        .requestMatchers("/teamMember/**" , "/styles/styleTeamMember/**").hasRole("TEAM_MEMBER")
                        .requestMatchers("/manager/**" , "/styles/styleManager/**").hasRole("MANAGER")
                        .requestMatchers("/shared/signUpUI" , "/shared/signUp").permitAll()
                        .requestMatchers("/shared/**" , "/styles/styleShared/**").hasAnyRole("MANAGER" , "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/shared/loginPage")
                        .permitAll()
                        .successHandler(customAuthenticationSuccessHandler())
                        .loginProcessingUrl("/shared/login")
                        .failureUrl("/shared/loginPage?error=true")
                )
                .logout(logout -> logout
                        .logoutUrl("/shared/logout")
                        .logoutSuccessUrl("/shared/loginPage")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return (request , response , authentication) -> {
            boolean isManager = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));
            boolean isUser = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
            if(isManager){
                response.sendRedirect("/manager/managerUI");
            }
            else if(isUser){
                response.sendRedirect("/user/userUI");
            }
            else{
                response.sendRedirect("/auth/login");
            }
        };
    }

}