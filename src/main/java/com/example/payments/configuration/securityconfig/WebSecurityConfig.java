package com.example.payments.configuration.securityconfig;

import com.example.payments.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final String adminAuthority = Role.ADMIN.toString();
    private final String clientAuthority = Role.CLIENT.toString();

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/_login").permitAll()
                .requestMatchers("/auth/register").permitAll()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**",
                        "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers("/auth/account").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers("/auth/logout").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers("/user/**").hasAnyAuthority(adminAuthority)
                .requestMatchers("/user/update").hasAnyAuthority(clientAuthority)
                .requestMatchers("/card/**").authenticated()
                .requestMatchers("/payment/**").authenticated()
                .requestMatchers("/request/**").authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/auth/_login")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
