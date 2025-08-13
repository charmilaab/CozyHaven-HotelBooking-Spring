package com.hexaware.OnlineFoodDeliverySys.exceptions;

public class TransportationNotFoundException extends RuntimeException {
    public TransportationNotFoundException(String message) {
        super(message);
    }
}