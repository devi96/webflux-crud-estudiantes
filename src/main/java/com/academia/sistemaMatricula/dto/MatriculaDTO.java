package com.academia.sistemaMatricula.dto;

import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.model.Estudiante;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatriculaDTO {

    private String id;
    @NotNull(message = "La fecha de matrícula no puede ser nula.")
    private LocalDateTime fechaMatricula;

    private EstudianteDTO estudiante;
    @NotEmpty(message = "La lista de cursos no puede estar vacía.")
    private List<CursoDTO> cursos;

    private Boolean estado;
}
