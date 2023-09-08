package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InsiderTradingException;
import com.techelevator.tenmo.exceptions.InsufficientFunds;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    List<Transfer> listTransfersByUser(String user);

    boolean transferFunds(String userFrom, String userTo, BigDecimal amount) throws InsufficientFunds, InsiderTradingException;

    boolean transfer(String userFrom, String userTo, BigDecimal amount);

    Transfer getTransferById(int transferId);


}
