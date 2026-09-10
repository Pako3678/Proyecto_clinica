package com.clinica.api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
public class Paciente extends Persona {

    private String obraSocial;

    private Integer numeroAfiliado;

    /**
     * Campo agregado respecto del modelo Swing original: el panel mostraba
     * "Alergias Conocidas" pero la clase Paciente.java no tenía ese dato.
     * Se incorpora acá para que la búsqueda tenga contenido real.
     */
    private String alergias;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private HistoriaClinica historiaClinica;

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Integer getNumeroAfiliado() {
        return numeroAfiliado;
    }

    public void setNumeroAfiliado(Integer numeroAfiliado) {
        this.numeroAfiliado = numeroAfiliado;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }

    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
        if (historiaClinica != null) {
            historiaClinica.setPaciente(this);
        }
    }
}
