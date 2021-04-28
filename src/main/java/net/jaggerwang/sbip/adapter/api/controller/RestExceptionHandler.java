package net.jaggerwang.sbip.adapter.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import net.jaggerwang.sbip.adapter.api.controller.dto.RootDTO;
import net.jaggerwang.sbip.usecase.exception.UnauthenticatedException;
import net.jaggerwang.sbip.usecase.exception.UnauthorizedException;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Jagger Wang
 */
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RootDTO handle(Throwable throwable) {
        throwable.printStackTrace();
        return new RootDTO("fail", throwable.toString());
    }

    @ExceptionHandler(UsecaseException.class)
    @ResponseStatus(HttpStatus.OK)
    public RootDTO handle(UsecaseException exception) {
        return new RootDTO("fail", exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RootDTO handle(NotFoundException exception) {
        return new RootDTO("not_found", exception.getMessage());
    }

    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RootDTO handle(UnauthenticatedException exception) {
        return new RootDTO("unauthenticated", exception.getMessage());
    }

    @ExceptionHandler({UnauthorizedException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RootDTO handle(UnauthorizedException exception) {
        return new RootDTO("unauthorized", exception.getMessage());
    }
}
