package net.jaggerwang.sbip.usecase.exception;

/**
 * @author Jagger Wang
 */
public class UsecaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsecaseException(String message) {
        super(message);
    }
}
