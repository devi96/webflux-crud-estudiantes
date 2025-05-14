package com.academia.sistemaMatricula.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstudianteDTO {

    private String id;

    @NotNull
    @Size(min = 2, max = 100)
    private String nombres;

    @NotNull
    @Size(min = 2, max = 100)
    private String apellidos;

    @NotNull
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos numéricos")
    private String dni;

    @NotNull
    @Min(value = 16)
    @Max(value = 150)
    private Integer edad;

}
