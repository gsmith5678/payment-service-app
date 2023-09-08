package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TenmoService {

    private final String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

    private static String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public TenmoService(String authToken, String url){
        this.authToken = authToken;
        this.API_BASE_URL = url;

    }

    public BigDecimal getBalance(){
        BigDecimal resultOutput = null;
        try{
            ResponseEntity<BigDecimal> response = restTemplate.exchange(API_BASE_URL + "accounts/balances", HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            resultOutput = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return resultOutput;
    }

    public Transfer getTransferByID(int transferID){
        Transfer outputTransfer = null;
        try{
            ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfers/" + transferID, HttpMethod.GET, makeAuthEntity(), Transfer.class);
            outputTransfer = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return outputTransfer;
    }

    public Transfer[] listTransfers(){
        Transfer[] outputTransfer = null;
        try{
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            outputTransfer = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return outputTransfer;
    }

    public User[] listUsers(){
        User[] outputTransfer = null;
        try{
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), User[].class);
            outputTransfer = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return outputTransfer;
    }

    public boolean makeTransaction(String username, BigDecimal amount){
        TransferDTO transferDTO = new TransferDTO(username, amount);
        boolean success = false;
        try{
            String response = restTemplate.postForObject(API_BASE_URL + "transfers", maketransferEntity(transferDTO), String.class);
            success = true;
        }catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    private HttpEntity<TransferDTO> maketransferEntity(TransferDTO transferDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transferDTO, headers);
    }

    /**
     * Returns an HttpEntity with the `Authorization: Bearer:` header
     */
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
