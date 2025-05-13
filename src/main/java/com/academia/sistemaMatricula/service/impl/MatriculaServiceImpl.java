package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Matricula;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.repository.IMatriculaRepo;
import com.academia.sistemaMatricula.service.IMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements IMatriculaService {

    private final IMatriculaRepo repo;

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
            existing.setEstado(updated.isEstado());
    }
}
