package com.toy.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.member.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.toy.member.security.filter.JwtFilter;
import com.toy.member.security.handler.JwtAccessDeniedHandler;
import com.toy.member.security.handler.JwtAuthenticationEntryPoint;
import com.toy.member.security.handler.LoginFailureHandler;
import com.toy.member.security.handler.LoginSuccessHandler;
import com.toy.member.security.provider.CustomAuthenticationProvider;
import com.toy.member.security.provider.JwtTokenProvider;
import com.toy.member.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomUserDetailsService customUserDetailsService;

    private final ObjectMapper objectMapper;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling((exceptionConfig) -> exceptionConfig
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                .headers((headerConfig) -> headerConfig
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/sign-up/**").permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(jsonIdPwAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication ->
                new CustomAuthenticationProvider(customUserDetailsService, passwordEncoder())
                        .authenticate(authentication);
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonIdPwAuthenticationFilter() {
        JsonUsernamePasswordAuthenticationFilter filter
                = new JsonUsernamePasswordAuthenticationFilter(objectMapper, loginSuccessHandler, loginFailureHandler);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

}
