package com.clinica.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.clinica.api.dto.PacienteRequestDTO;
import com.clinica.api.dto.PacienteResponseDTO;
import com.clinica.api.model.Paciente;
import com.clinica.api.repository.PacienteRepository;
import com.clinica.api.repository.TurnoRepository;

import jakarta.persistence.EntityNotFoundException;

@SuppressWarnings("null")
public class PacienteServiceTest {

    private PacienteRepository pacienteRepository;
    private TurnoRepository turnoRepository;
    private PacienteService pacienteService;

    @BeforeEach
    public void setUp() {
        pacienteRepository = Mockito.mock(PacienteRepository.class);
        turnoRepository = Mockito.mock(TurnoRepository.class);
        pacienteService = new PacienteService(pacienteRepository, turnoRepository);
    }

    @Test
    public void testCrearPacienteExitoso() {
        PacienteRequestDTO request = new PacienteRequestDTO(
                "Carlos", "Perez", 35123456, 2619876543L, "OSDE", 12345, "Ninguna"
        );

        Mockito.when(pacienteRepository.existsByDni(35123456)).thenReturn(false);

        Paciente pacienteGuardado = new Paciente();
        pacienteGuardado.setId(10L);
        pacienteGuardado.setNombre(request.getNombre());
        pacienteGuardado.setApellido(request.getApellido());
        pacienteGuardado.setDni(request.getDni());
        pacienteGuardado.setTelefono(request.getTelefono());
        pacienteGuardado.setObraSocial(request.getObraSocial());
        pacienteGuardado.setNumeroAfiliado(request.getNumeroAfiliado());
        pacienteGuardado.setAlergias(request.getAlergias());

        Mockito.when(pacienteRepository.save(Mockito.any(Paciente.class))).thenReturn(pacienteGuardado);

        PacienteResponseDTO response = pacienteService.crear(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(10L, response.getId());
        Assertions.assertEquals("Carlos", response.getNombre());
        Assertions.assertEquals(35123456, response.getDni());

        ArgumentCaptor<Paciente> captor = ArgumentCaptor.forClass(Paciente.class);
        Mockito.verify(pacienteRepository).save(captor.capture());
        Paciente captured = captor.getValue();
        Assertions.assertNotNull(captured.getHistoriaClinica());
    }

    @Test
    public void testCrearPacienteDniDuplicadoLanzaError() {
        PacienteRequestDTO request = new PacienteRequestDTO(
                "Carlos", "Perez", 35123456, null, null, null, null
        );

        Mockito.when(pacienteRepository.existsByDni(35123456)).thenReturn(true);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pacienteService.crear(request);
        });
    }

    @Test
    public void testActualizarPacienteExitoso() {
        Paciente existente = new Paciente();
        existente.setId(5L);
        existente.setNombre("Original");
        existente.setApellido("Apellido");
        existente.setDni(40000000);

        Mockito.when(pacienteRepository.findById(5L)).thenReturn(Optional.of(existente));
        Mockito.when(pacienteRepository.existsByDniAndIdNot(41000000, 5L)).thenReturn(false);
        Mockito.when(pacienteRepository.save(Mockito.any(Paciente.class))).thenAnswer(i -> i.getArgument(0));

        PacienteRequestDTO updateRequest = new PacienteRequestDTO(
                "Modificado", "ApellidoNuevo", 41000000, 2614445555L, "Swiss Medical", 999, "Polvo"
        );

        PacienteResponseDTO response = pacienteService.actualizar(5L, updateRequest);

        Assertions.assertEquals("Modificado", response.getNombre());
        Assertions.assertEquals(41000000, response.getDni());
        Assertions.assertEquals("Swiss Medical", response.getObraSocial());
    }

    @Test
    public void testEliminarPacienteExitoso() {
        Paciente paciente = new Paciente();
        paciente.setId(8L);

        Mockito.when(pacienteRepository.findById(8L)).thenReturn(Optional.of(paciente));

        pacienteService.eliminar(8L);

        Mockito.verify(turnoRepository).deleteByPacienteId(8L);
        Mockito.verify(pacienteRepository).delete(paciente);
    }

    @Test
    public void testEliminarPacienteNoExistenteLanzaNotFound() {
        Mockito.when(pacienteRepository.findById(999L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            pacienteService.eliminar(999L);
        });
    }

    @Test
    public void testListarTodos() {
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNombre("Ana");
        p.setApellido("Armas");
        p.setDni(38000000);

        Mockito.when(pacienteRepository.findAllByOrderByApellidoAscNombreAsc()).thenReturn(Collections.singletonList(p));

        List<PacienteResponseDTO> lista = pacienteService.listarTodos(null);
        Assertions.assertEquals(1, lista.size());
        Assertions.assertEquals("Ana", lista.get(0).getNombre());
    }
}
