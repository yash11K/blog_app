package com.mountblue.blogapp.security;

import com.mountblue.blogapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfiguration {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(bCryptPasswordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationSuccessHandler authenticationSuccessHandler,
                                           AuthenticationProvider authenticationProvider) throws Exception {
        http.authorizeHttpRequests(customizer ->
                customizer
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/user/**").hasRole("WEBUSER")
                        .requestMatchers("/home/new/**").hasRole("WEBUSER")
                        .requestMatchers("/writewise/api/post/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/writewise/api/new/**").hasRole("WEBUSER")
                        .requestMatchers(HttpMethod.POST, "/writewise/api/update/**").hasRole("WEBUSER")
                        .requestMatchers(HttpMethod.POST, "/writewise/api/delete/**").hasRole("WEBUSER")

                        .anyRequest().permitAll())
                .formLogin(form ->{
                    form.loginPage("/loginPage")
                            .loginProcessingUrl("/authenticate")
                            .successHandler(authenticationSuccessHandler)
                            .permitAll();

                })
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider);
        return http.build();
        }
}
