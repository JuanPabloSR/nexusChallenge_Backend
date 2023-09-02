package com.nexus.inventory.exceptions;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(String message) {
        super(message);
    }

}
