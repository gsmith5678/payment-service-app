package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {
    private String userTo;
    private BigDecimal amount;

    public TransferDTO(String userTo, BigDecimal amount){
        this.userTo = userTo;
        this.amount = amount;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
