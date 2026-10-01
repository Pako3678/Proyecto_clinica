package com.clinica.api.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinica.api.dto.PacienteBusquedaDTO;
import com.clinica.api.dto.PacienteRequestDTO;
import com.clinica.api.dto.PacienteResponseDTO;
import com.clinica.api.model.EvolucionMedica;
import com.clinica.api.model.HistoriaClinica;
import com.clinica.api.model.Medicamento;
import com.clinica.api.model.Paciente;
import com.clinica.api.model.Tratamiento;
import com.clinica.api.repository.PacienteRepository;
import com.clinica.api.repository.TurnoRepository;

import jakarta.persistence.EntityNotFoundException;
 
@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final TurnoRepository turnoRepository;

    public PacienteService(PacienteRepository pacienteRepository, TurnoRepository turnoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
    }

    @Transactional(readOnly = true)
    public List<PacienteResponseDTO> listarTodos(String filtro) {
        List<Paciente> pacientes;
        if (filtro != null && !filtro.trim().isEmpty()) {
            pacientes = pacienteRepository.buscarPorFiltro(filtro.trim());
        } else {
            pacientes = pacienteRepository.findAllByOrderByApellidoAscNombreAsc();
        }
        return pacientes.stream().map(this::convertirAResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public PacienteResponseDTO obtenerPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paciente con ID " + id));
        return convertirAResponseDTO(paciente);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public PacienteBusquedaDTO buscarPorDni(Integer dni) {
        Paciente paciente = pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró ningún paciente con DNI " + dni));

        HistoriaClinica historia = paciente.getHistoriaClinica();

        Set<String> medicamentos = new LinkedHashSet<>();
        Set<String> estudios = new LinkedHashSet<>();
        Set<String> medicos = new LinkedHashSet<>();

        if (historia != null && historia.getEvoluciones() != null) {
            for (EvolucionMedica evolucion : historia.getEvoluciones()) {
                if (evolucion.getMedicoAtendiente() != null) {
                    medicos.add(evolucion.getMedicoAtendiente().getNombre() + " "
                            + evolucion.getMedicoAtendiente().getApellido()
                            + " (" + evolucion.getMedicoAtendiente().getEspecialidad() + ")");
                }
                if (evolucion.getMedicamentosRecetados() != null) {
                    for (Medicamento m : evolucion.getMedicamentosRecetados()) {
                        medicamentos.add(m.getNombreComercial() + " - " + m.getDrogaFarma());
                    }
                }
                if (evolucion.getTratamientos() != null) {
                    for (Tratamiento t : evolucion.getTratamientos()) {
                        estudios.add(t.getNombre());
                    }
                }
            }
        }

        return new PacienteBusquedaDTO(
                historia != null ? historia.getId() : null,
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getDni(),
                paciente.getObraSocial(),
                paciente.getNumeroAfiliado(),
                paciente.getAlergias(),
                new ArrayList<>(medicamentos),
                new ArrayList<>(estudios),
                new ArrayList<>(medicos)
        );
    }

    @Transactional
    @SuppressWarnings("null")
    public PacienteResponseDTO crear(PacienteRequestDTO dto) {
        if (pacienteRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un paciente registrado con el DNI " + dto.getDni());
        }

        Paciente paciente = new Paciente();
        paciente.setNombre(dto.getNombre().trim());
        paciente.setApellido(dto.getApellido().trim());
        paciente.setDni(dto.getDni());
        paciente.setTelefono(dto.getTelefono());
        paciente.setObraSocial(dto.getObraSocial() != null ? dto.getObraSocial().trim() : null);
        paciente.setNumeroAfiliado(dto.getNumeroAfiliado());
        paciente.setAlergias(dto.getAlergias() != null ? dto.getAlergias().trim() : null);

        HistoriaClinica historia = new HistoriaClinica();
        paciente.setHistoriaClinica(historia);

        Paciente guardado = pacienteRepository.save(paciente);
        return convertirAResponseDTO(guardado);
    }

    @Transactional
    @SuppressWarnings("null")
    public PacienteResponseDTO actualizar(Long id, PacienteRequestDTO dto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paciente con ID " + id));

        if (pacienteRepository.existsByDniAndIdNot(dto.getDni(), id)) {
            throw new IllegalArgumentException("El DNI " + dto.getDni() + " ya pertenece a otro paciente registrado");
        }

        paciente.setNombre(dto.getNombre().trim());
        paciente.setApellido(dto.getApellido().trim());
        paciente.setDni(dto.getDni());
        paciente.setTelefono(dto.getTelefono());
        paciente.setObraSocial(dto.getObraSocial() != null ? dto.getObraSocial().trim() : null);
        paciente.setNumeroAfiliado(dto.getNumeroAfiliado());
        paciente.setAlergias(dto.getAlergias() != null ? dto.getAlergias().trim() : null);

        Paciente actualizado = pacienteRepository.save(paciente);
        return convertirAResponseDTO(actualizado);
    }

    @Transactional
    @SuppressWarnings("null")
    public void eliminar(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el paciente con ID " + id));

        turnoRepository.deleteByPacienteId(id);
        pacienteRepository.delete(paciente);
    }

    private PacienteResponseDTO convertirAResponseDTO(Paciente paciente) {
        Long idHistoria = paciente.getHistoriaClinica() != null ? paciente.getHistoriaClinica().getId() : null;
        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getApellido(),
                paciente.getDni(),
                paciente.getTelefono(),
                paciente.getObraSocial(),
                paciente.getNumeroAfiliado(),
                paciente.getAlergias(),
                idHistoria
        );
    }
}
