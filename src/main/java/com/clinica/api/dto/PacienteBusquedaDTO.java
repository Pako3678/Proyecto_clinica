package com.clinica.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteBusquedaDTO {

    private Long idReporte; // id de la HistoriaClinica
    private String nombre;
    private String apellido;
    private Integer dni;
    private String obraSocial;
    private Integer numeroAfiliado;
    private String alergias;

    private List<String> medicamentosRecetados;
    private List<String> estudiosRealizados; // proviene de Tratamiento
    private List<String> medicosVisitados;

    public PacienteBusquedaDTO() {
    }

    public PacienteBusquedaDTO(Long idReporte, String nombre, String apellido, Integer dni,
            String obraSocial, Integer numeroAfiliado, String alergias,
            List<String> medicamentosRecetados, List<String> estudiosRealizados,
            List<String> medicosVisitados) {
        this.idReporte = idReporte;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
        this.alergias = alergias;
        this.medicamentosRecetados = medicamentosRecetados;
        this.estudiosRealizados = estudiosRealizados;
        this.medicosVisitados = medicosVisitados;
    }

    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

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

    public List<String> getMedicamentosRecetados() {
        return medicamentosRecetados;
    }

    public void setMedicamentosRecetados(List<String> medicamentosRecetados) {
        this.medicamentosRecetados = medicamentosRecetados;
    }

    public List<String> getEstudiosRealizados() {
        return estudiosRealizados;
    }

    public void setEstudiosRealizados(List<String> estudiosRealizados) {
        this.estudiosRealizados = estudiosRealizados;
    }

    public List<String> getMedicosVisitados() {
        return medicosVisitados;
    }

    public void setMedicosVisitados(List<String> medicosVisitados) {
        this.medicosVisitados = medicosVisitados;
    }
}
