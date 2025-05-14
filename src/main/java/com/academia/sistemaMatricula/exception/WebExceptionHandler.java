package com.academia.sistemaMatricula.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
@Order(-1)
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse); //req -> renderErrorResponse(req)
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest req) {
        Map<String, Object> generalError = getErrorAttributes(req, ErrorAttributeOptions.defaults());

        Throwable ex = getError(req);
        int statusCode = Integer.valueOf(String.valueOf(generalError.get("status")));

        CustomErrorResponse cer;

        switch (statusCode) {
            case 400, 422 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "BAD REQUEST");
            }
            case 404 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "RESOURCE NOT FOUND");
            }
            case 401, 403 -> {
                cer = new CustomErrorResponse(LocalDateTime.now(), "RESOURCE NOT AUTHORIZED");
            }
            case 500 -> cer = new CustomErrorResponse(LocalDateTime.now(), "INTERNAL SERVER ERROR");
            default -> cer = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage());
        }

        return ServerResponse.status(statusCode)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(cer));
    }
}
