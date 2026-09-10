package com.clinica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.api.model.HistoriaClinica;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
}
