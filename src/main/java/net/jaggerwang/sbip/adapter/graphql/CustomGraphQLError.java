package net.jaggerwang.sbip.adapter.graphql;

import graphql.kickstart.spring.error.ThrowableGraphQLError;

public class CustomGraphQLError extends ThrowableGraphQLError {
    private static final long serialVersionUID = 1L;

    private String type;

    public CustomGraphQLError(Throwable throwable, String message, String type) {
        super(throwable, message);

        this.type = type;
    }

    public CustomGraphQLError(Throwable throwable, String type) {
        super(throwable, throwable.getMessage());

        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
