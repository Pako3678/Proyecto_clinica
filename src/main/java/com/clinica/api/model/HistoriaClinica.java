package com.clinica.api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "historias_clinicas")
@Getter
@Setter
@NoArgsConstructor
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    @JsonIgnore
    private Paciente paciente;

    // Una historia clínica tiene MUCHAS evoluciones a lo largo del tiempo
    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvolucionMedica> evoluciones = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<EvolucionMedica> getEvoluciones() {
        return evoluciones;
    }

    public void setEvoluciones(List<EvolucionMedica> evoluciones) {
        this.evoluciones = evoluciones;
    }
}
