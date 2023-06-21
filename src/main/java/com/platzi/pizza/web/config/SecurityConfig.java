package com.platzi.pizza.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http        //inicio de la configuracion de seguridad
                .csrf().disable()
                .authorizeHttpRequests()    //autorizando peticiones http
                .anyRequest()               //cualquier peticion que se haga
                //.permitAll();               //las permita todas
                .authenticated()
                .and()
                .httpBasic();

        return http.build();
    }

}
