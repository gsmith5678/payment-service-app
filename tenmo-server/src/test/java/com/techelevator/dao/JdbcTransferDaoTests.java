package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.exceptions.InsiderTradingException;
import com.techelevator.tenmo.exceptions.InsufficientFunds;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDaoTests extends BaseDaoTests {

    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";
    private static final String USER_3 = "user3";
    private JdbcTransferDao sut;
    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    @Before
    public void setup(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        userDao = new JdbcUserDao(jdbcTemplate);
        accountDao = new JdbcAccountDao(jdbcTemplate, userDao);
        sut = new JdbcTransferDao(jdbcTemplate, accountDao, userDao);

    }

    @Test
    public void transfer(){
        boolean result = sut.transfer("user1", "user2", BigDecimal.valueOf(1));
        Assert.assertTrue(result);
    }
    @Test
    public void transfer_ShouldReturnTrue_WhenTransferIsSuccessful() {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(1);

        // Act
        boolean result = sut.transfer(USER_1, USER_2, amount);

        // Assert
        Assert.assertTrue(result);
    }
    @Test
    public void transferFunds_ShouldThrowInsufficientFunds_WhenUserDoesNotHaveEnoughBalance() {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(10000);

        // Act & Assert
        Assert.assertThrows(InsufficientFunds.class, () -> {
            sut.transferFunds(USER_1, USER_2, amount);
        });
    }
    @Test
    public void transferFunds_ShouldThrowInsiderTradingException_WhenUserTriesToTransferToThemselves() {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(100);

        // Act & Assert
        Assert.assertThrows(InsiderTradingException.class, () -> {
            sut.transferFunds(USER_1, USER_1, amount);
        });
    }
    @Test
    public void listTranfersByUser_ShouldReturnListOfTransfers() {
        // Arrange
        List<Transfer> expectedTransfers = new ArrayList<>();
        Transfer transfer1 = new Transfer();
        transfer1.setTransferId(1);
        transfer1.setTransferType("Send");
        transfer1.setTransferStatus("Approved");
        transfer1.setAmount(BigDecimal.valueOf(10.0));
        transfer1.setUserFrom(USER_1);
        transfer1.setUserTo(USER_2);
        expectedTransfers.add(transfer1);
        Transfer transfer2 = new Transfer();
        transfer2.setTransferId(2);
        transfer2.setTransferType("Send");
        transfer2.setTransferStatus("Approved");
        transfer2.setAmount(BigDecimal.valueOf(20));
        transfer2.setUserFrom(USER_2);
        transfer2.setUserTo(USER_1);
        expectedTransfers.add(transfer2);
        sut.transfer(transfer1.getUserFrom(),transfer1.getUserTo(),BigDecimal.valueOf(10.0));
        sut.transfer(transfer2.getUserFrom(),transfer2.getUserTo(),BigDecimal.valueOf(20));
        // Act
        List<Transfer> actualTransfers = sut.listTransfersByUser(USER_1);

//        BigDecimal actualTransferAmmount = (actualTransfers.get(0).getAmount());
//        actualTransferAmmount.setScale(2);
        // Assert
        Assert.assertEquals(expectedTransfers.size(), actualTransfers.size());
        Assert.assertEquals(expectedTransfers.get(0).getTransferType(), actualTransfers.get(0).getTransferType());
        Assert.assertEquals(expectedTransfers.get(0).getTransferStatus(), actualTransfers.get(0).getTransferStatus());
        Assert.assertEquals(BigDecimal.valueOf(10.00).setScale(2), (actualTransfers.get(0).getAmount()));//Taking our extra 0 our of our Decimal
        Assert.assertEquals(expectedTransfers.get(0).getUserFrom(), actualTransfers.get(0).getUserFrom());
        Assert.assertEquals(expectedTransfers.get(0).getUserTo(), actualTransfers.get(0).getUserTo());

    }

    @Test
    public void getTransferById_ShouldReturnTransfer_WhenTransferExists() {
        // Arrange
        Transfer expectedTransfer = new Transfer();
        expectedTransfer.setTransferId(3003);
        expectedTransfer.setTransferType("Send");
        expectedTransfer.setTransferStatus("Approved");
        expectedTransfer.setAmount(BigDecimal.valueOf(10));
        expectedTransfer.setUserFrom(USER_1);
        expectedTransfer.setUserTo(USER_2);
        sut.transfer(expectedTransfer.getUserFrom(),expectedTransfer.getUserTo(),BigDecimal.valueOf(10));
        //TODO refactor our method to include the ID of the trasnfer.
        List<Transfer> trasnferlist =sut.listTransfersByUser(USER_1);
        // Act
        Transfer actualTransfer = sut.getTransferById(3003);
        // Assert
        Assert.assertEquals(expectedTransfer.getTransferType(), (actualTransfer.getTransferType()));
        Assert.assertEquals(expectedTransfer.getTransferStatus(), (actualTransfer.getTransferStatus()));
        Assert.assertEquals(BigDecimal.valueOf(10.00).setScale(2), (actualTransfer.getAmount()));
        Assert.assertEquals(expectedTransfer.getUserFrom(), (actualTransfer.getUserFrom()));
        Assert.assertEquals(expectedTransfer.getUserTo(), (actualTransfer.getUserTo()));
    }


    @Test
    public void getTransferById_ShouldReturnNull_WhenTransferDoesNotExist() {
        // Arrange
        long nonExistentTransferId = -1;

        // Act
        Transfer actualTransfer = sut.getTransferById((int) nonExistentTransferId);

        // Assert
        Assert.assertNull(actualTransfer);
    }

}






