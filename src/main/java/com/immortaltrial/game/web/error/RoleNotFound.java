package com.immortaltrial.game.web.error;

public class RoleNotFound extends RuntimeException {

    public RoleNotFound() {
        super();
    }

    public RoleNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RoleNotFound(final String message) {
        super(message);
    }

    public RoleNotFound(final Throwable cause) {
        super(cause);
    }
}
