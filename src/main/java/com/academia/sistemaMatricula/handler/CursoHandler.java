package com.academia.sistemaMatricula.handler;

import com.academia.sistemaMatricula.dto.CursoDTO;
import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.service.ICursoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class CursoHandler {

    private final ICursoService service;
    private final ModelMapper modelMapper;

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");

        return service.findById(id)
                .map(this::convertToDto)
                .flatMap( e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
    @GetMapping
    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<CursoDTO> fx = service.findAll()
                .map(this::convertToDto);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx, CursoDTO.class);
    }

    @PostMapping
    public Mono<ServerResponse> save(ServerRequest request){
        Mono<CursoDTO> monoCursoDTO = request.bodyToMono(CursoDTO.class);
        return monoCursoDTO
                .map(this::convertToEntity)
                .flatMap(service::save)
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<CursoDTO> monoCursoDTO = request.bodyToMono(CursoDTO.class);

        return monoCursoDTO
                .map(this::convertToEntity)
                .flatMap(curso -> service.update(id, curso))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.delete(id)
                .flatMap(result -> {
                    if(result){
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
                    }
                });
    }

    private CursoDTO convertToDto(Curso curso) {
        return modelMapper.map(curso, CursoDTO.class);
    }
    private Curso convertToEntity(CursoDTO cursoDTO) {
        return modelMapper.map(cursoDTO, Curso.class);
    }
}
