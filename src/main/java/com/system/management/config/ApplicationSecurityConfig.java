package com.system.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint("401"));
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").authenticated();
//                .and()
//                .authorizeRequests()
//                .antMatchers("/user/register","/user/login").permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/users/logout");
//                .antMatchers("/**").authenticated();
    }





    // The reason which I wrote this is that there is cors problem with browsers
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://77.71.76.17:8080", "http://77.71.76.17:4200", "http://192.168.0.100:4200"));
        configuration.setAllowedMethods(List.of("POST, GET, OPTIONS, DELETE"));
//        configuration.setAllowCredentials(true);
        //the below three lines will add the relevant CORS response headers
//        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
