package com.clinica.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evoluciones_medicas")
@Getter
@Setter
@NoArgsConstructor
public class EvolucionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se guarda cuándo se hizo la atención
    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medico_id")
    private Medico medicoAtendiente;

    private String diagnostico;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    @JsonIgnore
    private HistoriaClinica historiaClinica;

    // Listas opcionales de lo que se le recetó en ESA consulta
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "evolucion_tratamientos",
            joinColumns = @JoinColumn(name = "evolucion_id"),
            inverseJoinColumns = @JoinColumn(name = "tratamiento_id")
    )
    private List<Tratamiento> tratamientos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "evolucion_medicamentos",
            joinColumns = @JoinColumn(name = "evolucion_id"),
            inverseJoinColumns = @JoinColumn(name = "medicamento_id")
    )
    private List<Medicamento> medicamentosRecetados = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Medico getMedicoAtendiente() {
        return medicoAtendiente;
    }

    public void setMedicoAtendiente(Medico medicoAtendiente) {
        this.medicoAtendiente = medicoAtendiente;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public List<Medicamento> getMedicamentosRecetados() {
        return medicamentosRecetados;
    }

    public void setMedicamentosRecetados(List<Medicamento> medicamentosRecetados) {
        this.medicamentosRecetados = medicamentosRecetados;
    }
}
