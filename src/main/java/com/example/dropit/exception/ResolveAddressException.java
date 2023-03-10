package com.example.dropit.exception;

import lombok.EqualsAndHashCode;
import lombok.Generated;

@EqualsAndHashCode(callSuper = true)
@Generated
public class ResolveAddressException extends RuntimeException {
    public ResolveAddressException(String message) {
        super(message);
    }
    public ResolveAddressException(String message, Throwable cause) {
        super(message, cause);
    }
}
