package com.platzi.pizza.web.config;

import com.platzi.pizza.persintence.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  //anotacion para q esta clase sea incluida dentro de los bines
public class JwtFilter extends OncePerRequestFilter {   //filtro para verificar el JWT
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override   //este metodo capturara cada peticion que se haga por medio del controlador, y validar su autorizacion JWT
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Pasos de nuestro filtro para validar un JWT
        //1. Validar que sea un Header Authorization valido, encabezado valido.
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);    //respuesta como peticion invalida
            return;
        }

        //2. validar que el JWT sea valido
        String jwt = authHeader.split(" ")[1].trim();
        if (!this.jwtUtil.isValid(jwt)){
            filterChain.doFilter(request, response);    //no continuar xq no es valida el jwt
            return;
        }

        //3. Cargar el usuario del UserDetailsService
        String username = this.jwtUtil.getUsername(jwt);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        //4. Cargar al usuario en el contexto de seguridad
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), user.getAuthorities()
        );

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //guardar detalles en nuestro println
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);  //cargando autenticacion al contexto de seguridad
        System.out.println("->->->->->"+authenticationToken);
        filterChain.doFilter(request, response);
    }
}
