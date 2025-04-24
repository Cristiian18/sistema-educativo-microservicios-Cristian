package com.microservicios.asignaturas.controlador;

import com.microservicios.asignaturas.model.Asignatura;
import com.microservicios.asignaturas.servicio.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public ResponseEntity<List<Asignatura>> listarAsignaturas() {
        return ResponseEntity.ok(asignaturaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> obtenerAsignatura(@PathVariable Long id) {
        return asignaturaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Asignatura> crearAsignatura(@RequestBody Asignatura asignatura) {
        return new ResponseEntity<>(asignaturaService.guardar(asignatura), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> actualizarAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        if (!asignaturaService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        asignatura.setId(id);
        return ResponseEntity.ok(asignaturaService.guardar(asignatura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignatura(@PathVariable Long id) {
        if (!asignaturaService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        asignaturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<List<Asignatura>> buscarPorProfesor(@PathVariable Long profesorId) {
        return ResponseEntity.ok(asignaturaService.buscarPorProfesor(profesorId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Asignatura>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(asignaturaService.buscarPorNombre(nombre));
    }
}
