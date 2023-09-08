package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    @JsonIgnore

    private int transferTypeId;
    @JsonIgnore

    private int transferStatusId = 1;
    @JsonIgnore

    private int accountFrom;
    @JsonIgnore

    private int accountTo;
    private String transferStatus = "Approved";
    private String transferType = "Send";
    private BigDecimal amount;
    private String userTo;
    private String userFrom;

    public Transfer() {}

    public Transfer(String userFrom, String userTo, BigDecimal amount) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
    }

    public Transfer(int transferId, String transferType, String transferStatus, BigDecimal amount, String userFrom, String userTo){
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.amount = amount;
        this.userFrom = userFrom;
        this.userTo = userTo;
    }
    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
