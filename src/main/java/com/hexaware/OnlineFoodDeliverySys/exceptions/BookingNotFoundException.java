package com.hexaware.OnlineFoodDeliverySys.exceptions;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(String message) {
        super(message);
    }
}