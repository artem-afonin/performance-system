package ru.artem.perfsystem.exception;

public class LogParsingException extends RuntimeException {
    public LogParsingException() {
        super();
    }

    public LogParsingException(String message) {
        super(message);
    }

    public LogParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogParsingException(Throwable cause) {
        super(cause);
    }
}
