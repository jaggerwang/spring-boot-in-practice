package net.jaggerwang.sbip.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.api.exception.UnauthenticatedException;
import net.jaggerwang.sbip.api.exception.UnauthorizedException;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@ControllerAdvice
public class RestExceptionHandlers {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RootDto> handle(Exception exception) {
        exception.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RootDto("fail", exception.toString()));
    }

    @ExceptionHandler(UsecaseException.class)
    public ResponseEntity<RootDto> handle(UsecaseException exception) {
        return ResponseEntity.ok().body(new RootDto("fail", exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RootDto> handle(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RootDto("not_found", exception.getMessage()));
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<RootDto> handle(UnauthenticatedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new RootDto("unauthenticated", exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<RootDto> handle(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new RootDto("unauthorized", exception.getMessage()));
    }
}
