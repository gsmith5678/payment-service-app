package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "You can't send money to yourself... it's just weird. STOP IT!")
public class InsiderTradingException extends Exception {
    public InsiderTradingException(String message){
        super(message);
    }
}
