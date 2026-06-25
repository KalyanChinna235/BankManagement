package com.bank.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public String currentUser() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
