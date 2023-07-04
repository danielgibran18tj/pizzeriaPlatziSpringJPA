package com.platzi.pizza.persintence.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuditUserName implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()){
            return null;
        }

        String username = authentication.getPrincipal().toString();
        System.out.println("->->->"+ username + "  -  -> " + authentication.getAuthorities().toString());
        return Optional.of(username);
    }
}
