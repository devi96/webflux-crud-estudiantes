package com.academia.sistemaMatricula.handler;

import com.academia.sistemaMatricula.dto.EstudianteDTO;
import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
@Component
@RequiredArgsConstructor
public class EstudianteHandler {

    private final IEstudianteService service;
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
        Flux<EstudianteDTO> fx = service.findAll()
                .map(this::convertToDto);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx,EstudianteDTO.class);
    }

    @PostMapping
    public Mono<ServerResponse> save(ServerRequest request){
        Mono<EstudianteDTO> monoEstudianteDTO = request.bodyToMono(EstudianteDTO.class);
        return monoEstudianteDTO
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
        Mono<EstudianteDTO> monoEstudianteDTO = request.bodyToMono(EstudianteDTO.class);

        return monoEstudianteDTO
                .map(this::convertToEntity)
                .flatMap(Estudiante -> service.update(id, Estudiante))
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

    private EstudianteDTO convertToDto(Estudiante Estudiante) {
        return modelMapper.map(Estudiante, EstudianteDTO.class);
    }
    private Estudiante convertToEntity(EstudianteDTO EstudianteDTO) {
        return modelMapper.map(EstudianteDTO, Estudiante.class);
    }
}
