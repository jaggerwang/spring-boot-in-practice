package net.jaggerwang.sbip.usecase.exception;


/**
 * @author Jagger Wang
 */
public class UnauthorizedException extends UsecaseException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
