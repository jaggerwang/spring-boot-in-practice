package net.jaggerwang.sbip.adapter.graphql;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.jaggerwang.sbip.api.exception.UnauthenticatedException;
import net.jaggerwang.sbip.api.exception.UnauthorizedException;
import net.jaggerwang.sbip.usecase.exception.NotFoundException;
import net.jaggerwang.sbip.usecase.exception.UsecaseException;

@Component
public class GraphQLExceptionHandlers {
    @ExceptionHandler(Exception.class)
    public CustomGraphQLError handle(Exception exception) {
        exception.printStackTrace();

        return new CustomGraphQLError(exception, exception.toString(), "fail");
    }

    @ExceptionHandler(UsecaseException.class)
    public CustomGraphQLError handle(UsecaseException exception) {
        return new CustomGraphQLError(exception, "fail");
    }

    @ExceptionHandler(NotFoundException.class)
    public CustomGraphQLError handle(NotFoundException exception) {
        return new CustomGraphQLError(exception, "not_found");
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public CustomGraphQLError handle(UnauthenticatedException exception) {
        return new CustomGraphQLError(exception, "unauthenticated");
    }

    @ExceptionHandler(UnauthorizedException.class)
    public CustomGraphQLError handle(UnauthorizedException exception) {
        return new CustomGraphQLError(exception, "unauthorized");
    }
}
