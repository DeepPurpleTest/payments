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
        http.cors().and().csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/auth/_login").anonymous()
                .requestMatchers(HttpMethod.POST, "/auth/register").anonymous()
                .requestMatchers("/payments-swagger/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**",
                        "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/account").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers(HttpMethod.POST, "/auth/logout").hasAnyAuthority(adminAuthority, clientAuthority)
                .requestMatchers("/admin/user/**").hasAnyAuthority(adminAuthority)
                .requestMatchers("/admin/card/**").hasAnyAuthority(adminAuthority)
                .requestMatchers("/admin/request/**").hasAnyAuthority(adminAuthority)
                .requestMatchers("/client/user/**").hasAnyAuthority(clientAuthority)
                .requestMatchers("/client/card/**").hasAnyAuthority(clientAuthority)
                .requestMatchers("/client/payment/**").hasAnyAuthority(clientAuthority)
                .requestMatchers("/client/request/**").hasAnyAuthority(clientAuthority)
                .requestMatchers("/client/receipt/**").hasAnyAuthority(clientAuthority)
                .requestMatchers("/css/**").hasAnyAuthority(clientAuthority)
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
