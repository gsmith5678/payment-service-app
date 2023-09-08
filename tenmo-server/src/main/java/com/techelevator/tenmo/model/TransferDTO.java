package com.techelevator.tenmo.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class TransferDTO {
    @NotEmpty
    private String userTo;

    private BigDecimal amount;

    public TransferDTO(String userTo, BigDecimal amount){
        this.userTo = userTo;
        this.amount = amount;
    }

    public String getUserTo() {
        return userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
