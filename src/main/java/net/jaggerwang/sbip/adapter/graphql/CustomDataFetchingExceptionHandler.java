package net.jaggerwang.sbip.adapter.graphql;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;

public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
    @Override
    public void accept(DataFetcherExceptionHandlerParameters handlerParameters) {
        var path = handlerParameters.getPath();
        var exception = handlerParameters.getException();
        var location = handlerParameters.getField().getSourceLocation();
        var error = new CustomDataFetchingError(path, exception, location);
        handlerParameters.getExecutionContext().addError(error);
    }
}
