package com.clinica.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacienteResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Long telefono;
    private String obraSocial;
    private Integer numeroAfiliado;
    private String alergias;
    private Long idHistoriaClinica;

    public PacienteResponseDTO() {
    }

    public PacienteResponseDTO(Long id, String nombre, String apellido, Integer dni, Long telefono,
            String obraSocial, Integer numeroAfiliado, String alergias, Long idHistoriaClinica) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.obraSocial = obraSocial;
        this.numeroAfiliado = numeroAfiliado;
        this.alergias = alergias;
        this.idHistoriaClinica = idHistoriaClinica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
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

    public Long getIdHistoriaClinica() {
        return idHistoriaClinica;
    }

    public void setIdHistoriaClinica(Long idHistoriaClinica) {
        this.idHistoriaClinica = idHistoriaClinica;
    }
}
