package com.example.payments.configuration.securityconfig;

import com.example.payments.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .requestMatchers(HttpMethod.POST, "/auth/_login").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register").anonymous()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**",
                        "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/account").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers(HttpMethod.POST, "/auth/logout").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers("/user/**").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.PATCH, "/user/update").hasAnyAuthority(clientAuthority)
                .requestMatchers(HttpMethod.GET, "/card").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.GET, "/card/user/{id}").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.GET, "/card/{id}").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers(HttpMethod.GET, "/card/phone_number").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.POST, "/card/create").hasAnyAuthority(clientAuthority)
                .requestMatchers(HttpMethod.PATCH, "/card/update").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers(HttpMethod.DELETE, "/card/delete").hasAnyAuthority(clientAuthority)
                .requestMatchers("/payment/**").hasAnyAuthority(clientAuthority)
                .requestMatchers(HttpMethod.GET,"/request/{id}").hasAnyAuthority(clientAuthority, adminAuthority)
                .requestMatchers(HttpMethod.GET,"/request/all").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.GET,"/request/user").hasAnyAuthority(clientAuthority)
                .requestMatchers(HttpMethod.GET,"/request/phone_number").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.POST,"/request/create").hasAnyAuthority(clientAuthority)
                .requestMatchers(HttpMethod.PATCH,"/request/update").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.DELETE,"/request/delete").hasAnyAuthority(adminAuthority)
                .requestMatchers(HttpMethod.GET, "/rabbit/send").permitAll()
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
