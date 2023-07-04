package com.platzi.pizza.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true) //se usa al Controlar métodos con Method Security, conecta con los Service
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http        //inicio de la configuracion de seguridad
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //orden de que no almacene sesiones
                .authorizeHttpRequests()    //autorizando peticiones http

                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                //.requestMatchers(HttpMethod.GET, "/api/*").permitAll() //aplicando una regla para el GET

                .requestMatchers("/api/customers/**").hasAnyRole("ADMIN","CUSTOMER")
                .requestMatchers(HttpMethod.GET, "/api/pizzas/**").hasAnyRole("ADMIN","CUSTOMER") //permiso para todos
                .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN") //permiso solo para ADMIN
                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                .requestMatchers("/api/orders/random").hasAuthority("random_order") //esto es un permiso puntual
                .requestMatchers("/api/orders/**").hasRole("ADMIN")     //MUCHO OJO CON EL ORDEN DE ESTOS PERMISOS
                .anyRequest()               //cualquier peticion que se haga
                //.permitAll();               //las permita todas

                .authenticated()            //para las peticiones, se necesita autenticacion
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);   //indicando que se usara antes del escrito
                //.httpBasic();   reemplazamos este metodo de seguridad por jwtFilter

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
