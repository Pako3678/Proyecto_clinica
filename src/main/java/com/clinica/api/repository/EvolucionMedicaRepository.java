package com.clinica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.api.model.EvolucionMedica;

public interface EvolucionMedicaRepository extends JpaRepository<EvolucionMedica, Long> {
}
