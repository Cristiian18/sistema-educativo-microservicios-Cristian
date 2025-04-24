package com.sistemaeducativo.usuarios.seguridad;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
public class AuthResponse {
    private String token;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    // Constructor completo
    public AuthResponse(String token, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
    }

    // Constructor con solo token
    public AuthResponse(String token) {
        this.token = token;
    }
}
