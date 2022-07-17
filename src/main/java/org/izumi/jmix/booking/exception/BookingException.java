package org.izumi.jmix.booking.exception;

public class BookingException extends RuntimeException {
    public BookingException() {
        super();
    }

    public BookingException(final String message) {
        super(message);
    }

    public BookingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BookingException(final Throwable cause) {
        super(cause);
    }
}
