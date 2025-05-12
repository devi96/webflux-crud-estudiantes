package com.academia.sistemaMatricula.controller;

import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final IEstudianteService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<Estudiante>>> findAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll())
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(e-> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Estudiante>> save(@RequestBody Estudiante estudiante) {
        return service.save(estudiante).map(
                e-> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build()
        );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Estudiante>> update(@PathVariable String id, @RequestBody Estudiante estudiante) {
        return service.update(id, estudiante)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.delete(id)
                .flatMap(result -> {
                        if(result){
                           return Mono.just(ResponseEntity.noContent().build());
                        } else {
                            return Mono.just(ResponseEntity.notFound().build());
                        }
                });
        }
}

