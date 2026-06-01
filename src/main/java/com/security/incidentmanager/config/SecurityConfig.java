package com.security.incidentmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // ADDED
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/css/**", "/login").permitAll()

                        // API — admin only (UNCHANGED)
                        .requestMatchers("/api/**").hasRole("ADMIN")

                        // ADDED: POST (create, update, delete) — admin only
                        .requestMatchers(HttpMethod.POST, "/incidents/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/assets/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/analysts/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/tags/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/sla-policies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/reports/**").hasRole("ADMIN")

                        // ADDED: GET form pages — admin only
                        .requestMatchers("/incidents/new", "/incidents/*/edit").hasRole("ADMIN")
                        .requestMatchers("/analysts/new", "/analysts/*/edit").hasRole("ADMIN")
                        .requestMatchers("/tags/new", "/tags/*/edit").hasRole("ADMIN")
                        .requestMatchers("/sla-policies/new", "/sla-policies/*/edit").hasRole("ADMIN")

                        // Read access — both roles (UNCHANGED)
                        .requestMatchers("/incidents/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/analysts/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/tags/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/sla-policies/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/h2-console/**",
                                "/api/**"
                        )
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // UNCHANGED
        UserDetails analyst = User.builder()
                .username("analyst")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(analyst, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}