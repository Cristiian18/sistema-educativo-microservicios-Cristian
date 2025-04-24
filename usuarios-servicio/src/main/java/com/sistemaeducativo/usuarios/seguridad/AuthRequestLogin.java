package com.sistemaeducativo.usuarios.seguridad;

import lombok.Data;

@Data
public class AuthRequestLogin {
    private String username;
    private String password;
}
