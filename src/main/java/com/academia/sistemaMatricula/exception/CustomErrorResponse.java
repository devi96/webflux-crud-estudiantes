package com.academia.sistemaMatricula.exception;

import java.time.LocalDateTime;

public record CustomErrorResponse(
        LocalDateTime datetime,
        String message
) {
}
