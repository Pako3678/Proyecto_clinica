package com.clinica.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.api.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByDni(Integer dni);
    Optional<Medico> findByMatricula(Integer matricula);
}
