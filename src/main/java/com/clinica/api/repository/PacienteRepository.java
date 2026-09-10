package com.clinica.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinica.api.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByDni(Integer dni);

    boolean existsByDni(Integer dni);

    boolean existsByDniAndIdNot(Integer dni, Long id);

    List<Paciente> findAllByOrderByApellidoAscNombreAsc();

    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(p.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(p.obraSocial) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "CAST(p.dni AS string) LIKE CONCAT('%', :filtro, '%') " +
           "ORDER BY p.apellido ASC, p.nombre ASC")
    List<Paciente> buscarPorFiltro(@Param("filtro") String filtro);
}
