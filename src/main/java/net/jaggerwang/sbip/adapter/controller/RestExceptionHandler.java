package net.jaggerwang.sbip.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import net.jaggerwang.sbip.adapter.controller.dto.RootDto;
import net.jaggerwang.sbip.usecase.exception.UnauthenticatedException;
import net.jaggerwang.sbip.usecase.exception.UnauthorizedException;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RootDto handle(Throwable throwable) {
        throwable.printStackTrace();
        return new RootDto("fail", throwable.toString());
    }

    @ExceptionHandler(UsecaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public RootDto handle(UsecaseException exception) {
        return new RootDto("fail", exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RootDto handle(NotFoundException exception) {
        return new RootDto("not_found", exception.getMessage());
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RootDto handle(UnauthenticatedException exception) {
        return new RootDto("unauthenticated", exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RootDto handle(UnauthorizedException exception) {
        return new RootDto("unauthorized", exception.getMessage());
    }
}
