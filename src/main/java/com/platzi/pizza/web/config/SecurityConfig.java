package com.platzi.pizza.web.config;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true) //se usa al Controlar métodos con Method Security, conecta con los Service
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http        //inicio de la configuracion de seguridad
                .csrf().disable()
                .authorizeHttpRequests()    //autorizando peticiones http
                //.requestMatchers(HttpMethod.GET, "/api/*").permitAll() //aplicando una regla para el GET
                        //estamos permitiendo permiso a un nivel mas -> http://localhost:8080/api/pizzas
                .requestMatchers("/api/customer/**").hasAnyRole("ADMIN","CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/api/pizzas/**").hasAnyRole("ADMIN","CUSTOMER") //permiso para todos
                .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN") //permiso solo para ADMIN
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .requestMatchers("/api/orders/random").hasAuthority("random_order") //esto es un permiso puntual
                .requestMatchers("/api/orders/**").hasRole("ADMIN")     //MUCHO OJO CON EL ORDEN DE ESTOS PERMISOS
                .anyRequest()               //cualquier peticion que se haga
                //.permitAll();               //las permita todas
                .authenticated()            //para las peticiones, se necesita autenticacion
                .and()
                .httpBasic();

        return http.build();
    }

    /*@Bean
    public UserDetailsService memoryUsers(){
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin12"))
                .roles("ADMIN")
                .build();
        UserDetails customer = User.builder()
                .username("customer")
                .password(passwordEncoder().encode("customer12"))
                .roles("CUSTOMER")
                .build();
        return new InMemoryUserDetailsManager(admin, customer);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
