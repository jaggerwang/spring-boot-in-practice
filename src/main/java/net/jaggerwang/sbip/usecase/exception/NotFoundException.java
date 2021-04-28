package net.jaggerwang.sbip.usecase.exception;

/**
 * @author Jagger Wang
 */
public class NotFoundException extends UsecaseException {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}
