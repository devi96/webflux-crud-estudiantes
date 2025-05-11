package com.academia.sistemaMatricula.repository;


import com.academia.sistemaMatricula.model.Estudiante;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IEstudianteRepo extends IGenericRepo<Estudiante, String> {
    // ReactiveMongoRepository ya tiene los métodos básicos como save, findById, delete, etc.
    // Puedes agregar métodos personalizados si es necesario.
}
