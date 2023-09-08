package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Not enough Funds")
public class InsufficientFunds extends Exception {
    public InsufficientFunds(String message) {
        super(message);
    }

}
