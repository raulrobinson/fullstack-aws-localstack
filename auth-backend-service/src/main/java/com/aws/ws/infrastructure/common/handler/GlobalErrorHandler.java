package com.aws.ws.infrastructure.common.handler;

import com.aws.ws.domain.exception.*;
import com.aws.ws.infrastructure.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@Order(-2)
public class GlobalErrorHandler {

    public Mono<ServerResponse> handle(Throwable throwable, String messageId) {
        log.error("Exception captured globally: {}", throwable.toString());

        return switch (throwable) {

            case UnauthorizedException ex -> buildErrorResponse(
                    HttpStatus.UNAUTHORIZED,
                    messageId,
                    TechnicalMessage.UNAUTHORIZED,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.UNAUTHORIZED.getParameter()
                    ))
            );

            case BusinessException ex -> buildErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    messageId,
                    TechnicalMessage.BAD_REQUEST,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            ex.getParameter()
                    ))
            );

            case DuplicateResourceException ex -> buildErrorResponse(
                    HttpStatus.CONFLICT,
                    messageId,
                    TechnicalMessage.ALREADY_EXISTS,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            ex.getParameter()
                    ))
            );

            case NotFoundException ex -> buildErrorResponse(
                    HttpStatus.NOT_FOUND,
                    messageId,
                    TechnicalMessage.NOT_FOUND,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            ex.getParameter()
                    ))
            );

            case NoContentException ex -> buildErrorResponse(
                    HttpStatus.NO_CONTENT,
                    messageId,
                    TechnicalMessage.NO_CONTENT,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.NO_CONTENT.getParameter()
                    ))
            );

            case ProcessorException ex -> buildErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    messageId,
                    TechnicalMessage.INTERNAL_SERVER_ERROR,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.INTERNAL_SERVER_ERROR.getParameter()
                    ))
            );

            case TechnicalException ex -> buildErrorResponse(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    messageId,
                    TechnicalMessage.INTERNAL_ERROR_IN_ADAPTERS,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.INTERNAL_ERROR_IN_ADAPTERS.getParameter()
                    ))
            );

            case DatabaseResourceException ex -> buildErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    messageId,
                    TechnicalMessage.DATABASE_ERROR,
                    List.of(ErrorDTO.of(
                            ex.getMessage(),
                            TechnicalMessage.DATABASE_ERROR.getParameter()
                    ))
            );

            default -> buildErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    messageId,
                    TechnicalMessage.INTERNAL_SERVER_ERROR,
                    List.of(ErrorDTO.of(
                            throwable.getMessage(),
                            TechnicalMessage.INTERNAL_SERVER_ERROR.getParameter()
                    ))
            );
        };
    }

    public Mono<ServerResponse> handleAuth(String message) {
        log.error("Unauthorized access attempt detected");
        return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(APIResponse.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(TechnicalMessage.UNAUTHORIZED.getMessage())
                        .timestamp(Instant.now().toString())
                        .identifier(null) // No message ID for auth errors
                        .build());
    }

    private Mono<ServerResponse> buildErrorResponse(HttpStatus httpStatus,
                                                    String messageId,
                                                    TechnicalMessage technicalMessage,
                                                    List<ErrorDTO> errors) {
        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(APIResponse.builder()
                        .code(httpStatus.value())
                        .message(technicalMessage.getMessage())
                        .timestamp(Instant.now().toString())
                        .identifier(messageId)
                        .errors(errors)
                        .build());
    }
}
