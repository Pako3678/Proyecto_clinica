package com.clinica.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinica.api.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByPacienteDni(Integer dni);

    @Modifying
    @Query("DELETE FROM Turno t WHERE t.paciente.id = :pacienteId")
    void deleteByPacienteId(@Param("pacienteId") Long pacienteId);
}
