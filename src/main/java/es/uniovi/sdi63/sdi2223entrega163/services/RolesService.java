package es.uniovi.sdi63.sdi2223entrega163.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String[] roles = {"ROLE_ADMIN", "ROLE_USER"};

    public String[] getRoles() {
        return roles;
    }
}
