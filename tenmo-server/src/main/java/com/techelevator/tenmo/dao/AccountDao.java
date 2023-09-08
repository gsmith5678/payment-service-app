package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDao {

     BigDecimal getBalance (String username);
     boolean addBalance (String username, BigDecimal amount);
     //Account UserById(int id);
     boolean subtractBalance (String username, BigDecimal amount);

     Integer getAccountIdByUsername(String username);

}
