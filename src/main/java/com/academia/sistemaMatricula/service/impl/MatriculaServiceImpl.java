package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.model.Matricula;
import com.academia.sistemaMatricula.repository.IEstudianteRepo;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.repository.IMatriculaRepo;
import com.academia.sistemaMatricula.service.ICursoService;
import com.academia.sistemaMatricula.service.IMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements IMatriculaService {

    private final IMatriculaRepo repo;
    private final IEstudianteRepo repoEstudiante;
    private final ICursoService repoCurso;
    @Override
    protected IGenericRepo<Matricula, String> getRepo(){
        return repo;
    }

    @Override
    protected void applyUpdates(Matricula existing, Matricula updated) {
        if (updated.getFechaMatricula() != null) {
            existing.setFechaMatricula(updated.getFechaMatricula());
        }

        if (updated.getEstudiante() != null) {
            existing.setEstudiante(updated.getEstudiante());
        }

        if (updated.getCursos() != null) {
            existing.setCursos(updated.getCursos());
        }
        if (updated.getEstado() != null) {
            existing.setEstado(updated.getEstado());
        }
    }
    @Override
    public Mono<Matricula> registrarMatricula(Matricula matricula) {
        // Validamos que el Estudiante exista en la base de datos
        Mono<Estudiante> estudianteMono = repoEstudiante.findById(matricula.getEstudiante().getId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Estudiante no encontrado: " + matricula.getEstudiante().getId())));

        // Validamos que los Cursos existan en la base de datos
        Mono<List<Curso>> cursosMonos = Flux.fromIterable(matricula.getCursos())
                .flatMap(curso -> repoCurso.findById(curso.getId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cursos no encontrados")))
                .collectList();

        // Combinamos la validación del Estudiante y de los Cursos
        return Mono.zip(estudianteMono, cursosMonos)
                .flatMap(tuple -> {
                    return repo.save(matricula);
                });
    }
}
