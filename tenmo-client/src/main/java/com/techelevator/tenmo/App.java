package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TenmoService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;

    private final TenmoService tenmoService = new TenmoService("", API_BASE_URL);

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        boolean shouldContinue = true;
        while (shouldContinue) {
            UserCredentials credentials = consoleService.promptForCredentials();
            currentUser = authenticationService.login(credentials);
            if (currentUser != null) {
                String token = currentUser.getToken();
                currentUser.setToken(token);
                tenmoService.setAuthToken(token);
                shouldContinue = false;
            } else {
                consoleService.printErrorMessage();
                System.out.println("Invalid username or password.");
            }
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewTransferHistoryByTranferId();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        BigDecimal output = tenmoService.getBalance();
        System.out.println();
        System.out.println("Your current account balance is: " + String.format("$%.2f", output));
        System.out.println();
	}

	private void viewTransferHistoryByTranferId() {
        String prompt = "Please enter ID of Transfer you'd like to view: ";
        int transferID = consoleService.promptForInt(prompt);
        Transfer transfer = tenmoService.getTransferByID(transferID);
        if (transfer == null) {
            System.out.println("Not a valid transfer ID number");
        } else {
            System.out.println(transfer);
        }
    }

    private void viewTransferHistory() {
        Transfer[] output = tenmoService.listTransfers();
        for(Transfer transfer : output){
            System.out.println(transfer);
        }
    }

	private void sendBucks() {
        System.out.println();
       System.out.println("List Of Available Users: ");
       User[] listOfUsers = tenmoService.listUsers();
       boolean loop = true;
       String userLoggedIn;
       String userToTransferTo = null;
       BigDecimal amount = null;
       while(loop) {
           userToTransferTo = consoleService.selectUser(listOfUsers);
           userLoggedIn = currentUser.getUser().getUsername();
           if (userToTransferTo.equals(userLoggedIn)) {
               System.out.println();
               System.out.println("* Please select a user other than yourself *");
               System.out.println();
           } else {
               System.out.println();
               amount = (consoleService.promptForBigDecimal("Please enter the amount you would like to send: "));
               System.out.println();
               if (amount.compareTo(BigDecimal.valueOf(0)) > 0) {
                   break;
               } else {
                   System.out.println("Amount cannot be zero or negative.");
                   System.out.println();
               }
           }
       }
       boolean success = tenmoService.makeTransaction(userToTransferTo, amount);
       if(success){
           System.out.println("Transaction was successful. Thank you!");
       }
	}
}
