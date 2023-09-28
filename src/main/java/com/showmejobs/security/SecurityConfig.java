package com.showmejobs.security;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.showmejobs.security.jwt.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disabling CSRF protection
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/user/**")).permitAll()
                        .requestMatchers(pathMatchers()).permitAll() // Permit OPTIONS requests
                        .requestMatchers(new AntPathRequestMatcher("/actuator/info"), new AntPathRequestMatcher("/actuator/health")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Helper method to create RequestMatcher for specific HTTP method and pattern
    private RequestMatcher pathMatchers() {
        return new AntPathRequestMatcher("/**", HttpMethod.OPTIONS.name());
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
            }
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/swagger-ui.html"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/index.html"),
                new AntPathRequestMatcher("/css/**"),
                new AntPathRequestMatcher("/js/**")
        );
    }


    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return
                http.getSharedObject(AuthenticationManagerBuilder.class)
                        .authenticationProvider(authProvider()).build();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


}
