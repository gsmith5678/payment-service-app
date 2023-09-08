package com.techelevator.tenmo.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class Account {
    @NotEmpty
    private int accountId;
    @NotEmpty
    private int userId;
    private BigDecimal balance;

    public Account(int accountId, int userId, BigDecimal amount) {
    }
}
