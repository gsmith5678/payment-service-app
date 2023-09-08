package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {
    // getBalance, listUsers, findById, Transfer

private JdbcAccountDao accountDao;
private JdbcUserDao userDao;

public AccountController(JdbcAccountDao accountDao, JdbcUserDao userDao){
    this.accountDao = accountDao;
    this.userDao = userDao;
}

@RequestMapping(path = "/accounts/balances", method = RequestMethod.GET)
public BigDecimal getBalance(Principal principal) {
    String username = principal.getName();
    return accountDao.getBalance(username);
}

@RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

}
