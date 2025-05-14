package com.academia.sistemaMatricula.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDTO {

    private String id;

    @NotNull
    @Size(min = 2, max = 100)
    private String nombre;

    @Size(min = 2, max = 10)
    private String siglas;

    private Boolean estado;

}
