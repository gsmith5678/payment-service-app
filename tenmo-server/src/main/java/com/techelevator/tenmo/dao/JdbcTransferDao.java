package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InsiderTradingException;
import com.techelevator.tenmo.exceptions.InsufficientFunds;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserDao userDao;
    private AccountDao accountDao;


    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao, JdbcUserDao userDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @Override
    public boolean transfer(String userFrom, String userTo, BigDecimal amount) {
        boolean success = false;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?, ?, ?, ?, ?);";
        success = jdbcTemplate.update(sql, 2, 2, accountDao.getAccountIdByUsername(userFrom), accountDao.getAccountIdByUsername(userTo), amount) > 0;
        return success;
    }

    @Override
    public boolean transferFunds(String userFrom, String userTo, BigDecimal amount) throws InsufficientFunds, InsiderTradingException {
        boolean success = false;
        if (userFrom.equals(userTo)) {
            throw new InsiderTradingException("Can't transfer to self");
        }
        if (accountDao.getBalance(userFrom).compareTo(amount) < 0 || amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            throw new InsufficientFunds("not enough funds");
        }
        boolean sub = accountDao.subtractBalance(userFrom, amount);
        boolean add = accountDao.addBalance(userTo, amount);
        boolean trans = transfer(userFrom, userTo, amount);

        if (add && sub && trans) {
            success = true;
        }
        return success;
    }

    @Override
    public List<Transfer> listTransfersByUser(String user) {
        List<Transfer> outputList = new ArrayList<>();
        int userId = userDao.findIdByUsername(user);
        String sql = sqlDefault +
                "WHERE af.user_id = ? OR at.user_id = ?\n" +
                "ORDER BY t.transfer_id;\n";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            outputList.add(transfer);
        }
        return outputList;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        String sql = sqlDefault + "WHERE t.transfer_id = ? \n;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
         Transfer transfer = mapRowToTransfer(results);
            return transfer;
        } else {
            return null;
        }
    }

    private String sqlDefault = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount,\n" +
            "    uf.username AS user_from, ut.username AS user_to\n" +
            "FROM transfer AS t\n" +
            "INNER JOIN account AS af ON af.account_id = t.account_from\n" +
            "INNER JOIN account AS at ON at.account_id = t.account_to\n" +
            "INNER JOIN tenmo_user AS uf ON uf.user_id = af.user_id\n" +
            "INNER JOIN tenmo_user AS ut ON ut.user_id = at.user_id\n" +
            "INNER JOIN transfer_type AS tt ON tt.transfer_type_id = t.transfer_type_id\n" +
            "INNER JOIN transfer_status AS ts ON ts.transfer_status_id = t.transfer_status_id\n";

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferType(results.getString("transfer_type_desc"));
        transfer.setTransferStatus(results.getString("transfer_status_desc"));
        transfer.setAmount(results.getBigDecimal("amount"));
        transfer.setUserFrom(results.getString("user_from"));
        transfer.setUserTo(results.getString("user_to"));

        return transfer;
    }
}



