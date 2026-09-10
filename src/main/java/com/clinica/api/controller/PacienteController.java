package com.clinica.api.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.api.dto.PacienteBusquedaDTO;
import com.clinica.api.dto.PacienteRequestDTO;
import com.clinica.api.dto.PacienteResponseDTO;
import com.clinica.api.service.PacienteService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listar(@RequestParam(name = "q", required = false) String q) {
        return ResponseEntity.ok(pacienteService.listarTodos(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    @GetMapping("/buscar/{dni}")
    public ResponseEntity<PacienteBusquedaDTO> buscarPorDni(@PathVariable("dni") Integer dni) {
        return ResponseEntity.ok(pacienteService.buscarPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crear(@Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO creado = pacienteService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizar(
            @PathVariable("id") Long id,
            @Valid @RequestBody PacienteRequestDTO dto) {
        return ResponseEntity.ok(pacienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable("id") Long id) {
        pacienteService.eliminar(id);
        return ResponseEntity.ok(Collections.singletonMap("mensaje", "Paciente eliminado correctamente"));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put("error", error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}
