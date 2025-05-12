package com.academia.sistemaMatricula.controller;

import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.service.ICursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {
    private final ICursoService service;

    @GetMapping("{id}")
    public Mono<ResponseEntity<Curso>> findById(@PathVariable String id){
        return service.findById(id)
                .map( e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping
    public Mono<ResponseEntity<Flux<Curso>>> findAll() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll())
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Curso>> save(@RequestBody Curso curso){
        return service.save(curso)
                .map( e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Curso>> update(@PathVariable String id, @RequestBody Curso curso) {
        return service.update(id, curso)
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
