package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.exceptions.InsiderTradingException;
import com.techelevator.tenmo.exceptions.InsufficientFunds;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
    private JdbcTransferDao transferDao;
    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    public TransferController(JdbcTransferDao transferDao, JdbcUserDao userDao, JdbcAccountDao accountDao){
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public void transferFund(@Valid @RequestBody TransferDTO transferDTO, Principal principal) throws InsufficientFunds, InsiderTradingException {
        String username = principal.getName();
        transferDao.transferFunds(username, transferDTO.getUserTo(), transferDTO.getAmount());
    }
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> listTransfers(Principal principal){
        String username = principal.getName();
        return transferDao.listTransfersByUser(username);
    }
    @RequestMapping(path = "/transfers/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferById(transferId);
    }

}
