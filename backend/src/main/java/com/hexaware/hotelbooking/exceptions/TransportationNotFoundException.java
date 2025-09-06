package com.hexaware.hotelbooking.exceptions;

public class TransportationNotFoundException extends RuntimeException {
    public TransportationNotFoundException(String message) {
        super(message);
    }
}