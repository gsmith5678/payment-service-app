package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao userDao;
    public JdbcAccountDao(JdbcTemplate jdbcTemplate, JdbcUserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = userDao;
    }

    @Override
    public BigDecimal getBalance(String username) {
        BigDecimal balance = null;
        String sql = "SELECT a.balance FROM account as a JOIN tenmo_user as te ON te.user_id = a.user_id \n" +
                "WHERE username = ?;";
        Double results = jdbcTemplate.queryForObject(sql, Double.class, username);
        if (results != null) {
            balance = BigDecimal.valueOf(results);
        }
        return balance;
    }

    @Override
    public boolean addBalance(String username, BigDecimal amount) {
        boolean success = false;
        int userId = userDao.findIdByUsername(username);
        String sql = "UPDATE account\n" +
                "SET balance = (balance + ?)\n" +
                "WHERE user_id = ?;";
        success = jdbcTemplate.update(sql, amount, userId) == 1;
        return success;
    }

    @Override
    public boolean subtractBalance(String username, BigDecimal amount) {
        //int userId = userDao.findIdByUsername(principal.getName());
        boolean success = false;
        int userId = userDao.findIdByUsername(username);
        String sql = "UPDATE account\n" +
                "SET balance = (balance - ?)\n" +
                "WHERE user_id = ?;";
        success = jdbcTemplate.update(sql, amount, userId) == 1;
        return success;
    }

    @Override
    public Integer getAccountIdByUsername(String username) {
        String sql = "SELECT account_id FROM account a JOIN tenmo_user u ON a.user_id = u.user_id WHERE u.username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return results.getInt("account_id");
        } else {
            return null;
        }
    }

}




