package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{

/*    protected static final Account ACCT_1 = new Account(1001, 1001, BigDecimal.valueOf(1000.0));
    protected static final Account ACCT_2 = new Account(1002, 1002, BigDecimal.valueOf(2000.0));
    private static final Account ACCT_3 = new Account(1003, 1003, BigDecimal.valueOf(500.0));
    protected static final User USER_1 = new User(1001, "user1", "user1", "USER");
    protected static final User USER_2 = new User(1002, "user2", "user2", "USER");
    private static final User USER_3 = new User(1003, "user3", "user3", "USER");*/
    private static final String USER_1 = "user1";

    private JdbcAccountDao sut;
    private JdbcUserDao userDao;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDao = new JdbcUserDao(jdbcTemplate);
        sut = new JdbcAccountDao(jdbcTemplate, userDao);
    }

    @Test
    public void getBalance(){
        BigDecimal result = sut.getBalance("user1");
        BigDecimal expected = BigDecimal.valueOf(1000.0);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSubtractBalance() {
        // Arrange
        String username = "user1";
        BigDecimal amount = BigDecimal.valueOf(100.0);
        BigDecimal expectedBalance = BigDecimal.valueOf(900.0);

        // Act
        boolean success = sut.subtractBalance(username, amount);
        BigDecimal actualBalance = sut.getBalance(username);

        // Assert
        Assert.assertTrue(success);
        Assert.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void testAddBalance() {
        // Arrange
        String username = "user1";
        BigDecimal amountToAdd = BigDecimal.valueOf(200.0);
        BigDecimal expectedBalance = BigDecimal.valueOf(1200.0);

        // Act
        boolean success = sut.addBalance(username, amountToAdd);
        BigDecimal actualBalance = sut.getBalance(username);

        // Assert
        Assert.assertTrue(success);
        Assert.assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void testGetAccountIdByUsername() {
        // Arrange
        String username = "user1";
        Integer expectedAccountId = 2001;

        // Act
        Integer actualAccountId = sut.getAccountIdByUsername(username);

        // Assert
        Assert.assertEquals(expectedAccountId, actualAccountId);
    }

}
