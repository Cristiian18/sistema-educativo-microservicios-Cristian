package com.microservicios.matriculas.service;

import com.microservicios.matriculas.dto.AsignaturaDTO;
import com.microservicios.matriculas.dto.EstudianteDTO;
import com.microservicios.matriculas.dto.MatriculaRequest;
import com.microservicios.matriculas.dto.Matricula_completaDTO;
import com.microservicios.matriculas.client.UsuarioFeignClient;
import com.microservicios.matriculas.client.AsignaturaFeignClient;
import com.microservicios.matriculas.Model.Matricula;
import com.microservicios.matriculas.repository.MatriculaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioFeignClient usuarioFeignClient;
    private final AsignaturaFeignClient asignaturaFeignClient;
    private final HttpServletRequest request;

    @Value("${security.token.asignaturas}")
    private String tokenAsignaturas;

    public MatriculaServiceImpl(
            MatriculaRepository matriculaRepository,
            UsuarioFeignClient usuarioFeignClient,
            AsignaturaFeignClient asignaturaFeignClient,
            HttpServletRequest request
    ) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioFeignClient = usuarioFeignClient;
        this.asignaturaFeignClient = asignaturaFeignClient;
        this.request = request;
    }

    @Override
    public Matricula registrar(Matricula matricula) {
        matricula.setFechaMatricula(LocalDateTime.now());
        matricula.setEstado("ACTIVA");
        return matriculaRepository.save(matricula);
    }

    @Override
    public List<Matricula> listar() {
        return matriculaRepository.findAll();
    }

    @Override
    public List<Matricula> findAll() {
        return matriculaRepository.findAll();
    }

    @Override
    public Optional<Matricula> findById(Long id) {
        return matriculaRepository.findById(id);
    }

    @Override
    public Matricula save(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    @Override
    public void deleteById(Long id) {
        matriculaRepository.deleteById(id);
    }

    @Override
    public Matricula crearMatricula(MatriculaRequest requestDto) {
        System.out.println("TOKEN DE USUARIO (Authorization): " + request.getHeader("Authorization"));
        System.out.println("TOKEN DE ASIGNATURAS (X-Token-Asig): " + tokenAsignaturas);

        // Validar estudiante
        EstudianteDTO estudiante = usuarioFeignClient.getEstudiante(requestDto.getEstudianteId());
        if (estudiante == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Estudiante no encontrado con ID: " + requestDto.getEstudianteId());
        }

        // Validar asignatura
        AsignaturaDTO asignatura = asignaturaFeignClient.obtenerAsignatura(requestDto.getAsignaturaId(), tokenAsignaturas);
        if (asignatura == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Asignatura no encontrada con ID: " + requestDto.getAsignaturaId());
        }

        if (asignatura.getCuposDisponibles() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "No hay cupos disponibles para esta asignatura.");
        }

        // Registrar la matrícula
        Matricula matricula = new Matricula();
        matricula.setEstudianteId(requestDto.getEstudianteId());
        matricula.setAsignaturaId(requestDto.getAsignaturaId());
        matricula.setFechaMatricula(LocalDateTime.now());
        matricula.setPeriodoAcademico(requestDto.getPeriodoAcademico());
        matricula.setEstado("ACTIVA");

        Matricula guardada = matriculaRepository.save(matricula);

        // Reducir el cupo
        asignaturaFeignClient.reducirCupo(requestDto.getAsignaturaId(), tokenAsignaturas);

        return guardada;
    }

    @Override
    public Matricula_completaDTO convertToMatriculaCompletaDTO(Matricula matricula) {
        EstudianteDTO estudiante = usuarioFeignClient.getEstudiante(matricula.getEstudianteId());
        AsignaturaDTO asignatura = asignaturaFeignClient.obtenerAsignatura(matricula.getAsignaturaId(), tokenAsignaturas);

        return new Matricula_completaDTO(
                matricula.getId(),
                estudiante,
                asignatura,
                matricula.getFechaMatricula(),
                matricula.getPeriodoAcademico(),
                matricula.getEstado()
        );
    }

    @Override
    public EstudianteDTO getEstudiante(Long id) {
        return usuarioFeignClient.getEstudiante(id);
    }

    @Override
    public AsignaturaDTO getAsignatura(Long id) {
        return asignaturaFeignClient.obtenerAsignatura(id, tokenAsignaturas);
    }
}
