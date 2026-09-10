package com.clinica.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
    private String apellido;

    @NotNull(message = "El DNI es obligatorio")
    @Min(value = 1000000, message = "El DNI debe tener al menos 7 dígitos")
    @Max(value = 99999999, message = "El DNI no puede superar los 8 dígitos")
    private Integer dni;

    private Long telefono;

    @Size(max = 100, message = "La obra social no puede superar los 100 caracteres")
    private String obraSocial;

    private Integer numeroAfiliado;

    @Size(max = 255, message = "Las alergias no pueden superar los 255 caracteres")
    private String alergias;


}
