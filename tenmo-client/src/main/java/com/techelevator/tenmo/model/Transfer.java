package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Transfer {
    List<Transfer> transferLists = new ArrayList<>();
    private int transferId;
    private String transferStatus;
    private String transferType;
    private BigDecimal amount;
    private String userTo;
    private String userFrom;

    public Transfer(){}
    public Transfer(int transferId, String transferStatus, String transferType, BigDecimal amount, String userTo, String userFrom) {
        this.transferId = transferId;
        this.transferStatus = transferStatus;
        this.transferType = transferType;
        this.amount = amount;
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
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

    @Override
    public String toString() {
        return "\n--------------------------------------------" +
                "\nTransaction Details: " +
                "\n--------------------------------------------" + '\n' +
                String.format("%-30s%-30s", "TransferId:", transferId) + '\n' +
                String.format("%-30s%-30s", "TransferStatus:", transferStatus) + '\n' +
                String.format("%-30s%-30s", "TransferType:", transferType) + '\n' +
                String.format("%-30s%-30s", "Amount:", "$"+amount) + '\n' +
                String.format("%-30s%-30s", "UserTo:", userTo) + '\n' +
                String.format("%-30s%-30s", "UserFrom:", userFrom) + '\n' +
        "--------------------------------------------";
    }
}
