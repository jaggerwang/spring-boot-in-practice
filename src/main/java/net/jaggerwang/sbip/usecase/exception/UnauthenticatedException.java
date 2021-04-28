package net.jaggerwang.sbip.usecase.exception;


/**
 * @author Jagger Wang
 */
public class UnauthenticatedException extends UsecaseException {
    private static final long serialVersionUID = 1L;

    public UnauthenticatedException(String message) {
        super(message);
    }
}
