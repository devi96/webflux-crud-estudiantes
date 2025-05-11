package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.repository.IEstudianteRepo;
import com.academia.sistemaMatricula.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements IEstudianteService {

    private final IEstudianteRepo estudianteRepo;

    @Override
    public Mono<Estudiante> save(Estudiante estudiante) {
        return estudianteRepo.save(estudiante);
    }

    @Override
    public Mono<Estudiante> update(String id, Estudiante estudiante) {
        return estudianteRepo.findById(id)
                .flatMap( encontrado -> {
                    encontrado.setNombres(estudiante.getNombres());
                    encontrado.setApellidos(estudiante.getApellidos());
                    encontrado.setEdad(estudiante.getEdad());
                    encontrado.setDni(estudiante.getDni());
                    return estudianteRepo.save(encontrado);
                }).switchIfEmpty( Mono.empty());
    }

    @Override
    public Flux<Estudiante> findAll() {
        return estudianteRepo.findAll();
    }

    @Override
    public Mono<Estudiante> findById(String id) {
        return estudianteRepo.findById(id);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return estudianteRepo.deleteById(id).thenReturn(true);
    }
}
