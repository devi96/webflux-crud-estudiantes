package com.academia.sistemaMatricula.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "estudiantes")
public class Estudiante extends IdentificableEntity<String> {
    private String nombres;
    private String apellidos;
    private String dni;
    private Integer edad;
}
