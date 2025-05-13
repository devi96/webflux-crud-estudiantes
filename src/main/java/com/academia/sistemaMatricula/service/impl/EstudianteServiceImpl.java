package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.repository.IEstudianteRepo;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements IEstudianteService {

    private final IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo(){
     return repo;
    }
    @Override
    protected void applyUpdates(Estudiante existing, Estudiante updated) {
        if (updated.getNombres() != null && !updated.getNombres().isBlank()) {
            existing.setNombres(updated.getNombres());
        }

        if (updated.getApellidos() != null && !updated.getApellidos().isBlank()) {
            existing.setApellidos(updated.getApellidos());
        }

        if (updated.getEdad() > 0) {
            existing.setEdad(updated.getEdad());
        }

        if (updated.getDni() != null && !updated.getDni().isBlank()) {
            existing.setDni(updated.getDni());
        }
    }
}
