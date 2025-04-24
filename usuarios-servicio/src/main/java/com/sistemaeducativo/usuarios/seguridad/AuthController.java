package com.sistemaeducativo.usuarios.seguridad;

import com.sistemaeducativo.usuarios.seguridad.AuthRequest;
import com.sistemaeducativo.usuarios.model.Estudiante;
import com.sistemaeducativo.usuarios.model.Profesor;
import com.sistemaeducativo.usuarios.model.Usuario;
import com.sistemaeducativo.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestLogin request) {
        try {
            // Autenticar credenciales
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Obtener detalles del usuario autenticado
            final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(request.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Crear respuesta con token, username y roles
            AuthResponse response = new AuthResponse(
                    jwt,
                    userDetails.getUsername(),
                    userDetails.getAuthorities()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String REGISTER_TOKEN = "TOKEN_DE_PRUEBA_ABC123";

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {

        if (!REGISTER_TOKEN.equals(authRequest.getToken())) {
            return ResponseEntity.status(403).body("Error: Token de registro inválido");

        }

        if (authRequest.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body("Error: La contraseña debe tener al menos 8 caracteres");
        }

        String rol = authRequest.getRol();
        if (rol == null || rol.isBlank()) {
            return ResponseEntity.badRequest().body("Error: El rol es obligatorio (ADMIN, PROFESOR o ESTUDIANTE)");
        }

        rol = rol.toUpperCase();
        List<String> rolesValidos = List.of("ADMIN", "PROFESOR", "ESTUDIANTE");
        if (!rolesValidos.contains(rol)) {
            return ResponseEntity.badRequest().body("Error: Rol inválido. Debe ser ADMIN, PROFESOR o ESTUDIANTE");
        }

        // Verificar si el usuario ya existe (por username o email)
        boolean existe = usuarioService
                .listarTodos()
                .stream()
                .anyMatch(u ->
                        u.getUsername().equalsIgnoreCase(authRequest.getUsername()) ||
                                u.getEmail().equalsIgnoreCase(authRequest.getEmail())
                );

        if (existe) {
            return ResponseEntity.badRequest().body("Error: El usuario ya existe (username o email duplicado)");
        }

        switch (rol) {
            case "ESTUDIANTE":
                Estudiante estudiante = new Estudiante();
                estudiante.setUsername(authRequest.getUsername());
                estudiante.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                estudiante.setRol("ESTUDIANTE");
                estudiante.setEmail(authRequest.getEmail());
                estudiante.setNombre(authRequest.getNombre());
                estudiante.setTelefono(authRequest.getTelefono());
                estudiante.setCarrera(authRequest.getCarrera());
                estudiante.setSemestre(authRequest.getSemestre());
                usuarioService.saveEstudiante(estudiante);
                break;

            case "PROFESOR":
                Profesor profesor = new Profesor();
                profesor.setUsername(authRequest.getUsername());
                profesor.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                profesor.setRol("PROFESOR");
                profesor.setEmail(authRequest.getEmail());
                profesor.setNombre(authRequest.getNombre());
                profesor.setTelefono(authRequest.getTelefono());
                profesor.setEspecialidad(authRequest.getEspecialidad());
                profesor.setDepartamento(authRequest.getDepartamento());
                usuarioService.saveProfesor(profesor);
                break;

            case "ADMIN":
                Usuario admin = new Usuario();
                admin.setUsername(authRequest.getUsername());
                admin.setPassword(passwordEncoder.encode(authRequest.getPassword()));
                admin.setRol("ADMIN");
                admin.setEmail(authRequest.getEmail());
                admin.setNombre(authRequest.getNombre());
                admin.setTelefono(authRequest.getTelefono());
                usuarioService.guardarUsuario(admin);
                break;
        }

        return ResponseEntity.ok("¡Usuario registrado con éxito como " + rol + "!");
    }
}
