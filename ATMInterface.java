import java.util.ArrayList;
import java.util.Scanner;

// Account class to store user information
class Account {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with balance: $" + initialBalance);
    }

    public boolean validateUser(String userId, String pin) {
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount + " | Balance: $" + balance);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        } else {
            balance -= amount;
            transactionHistory.add("Withdrawn: $" + amount + " | Balance: $" + balance);
            return true;
        }
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        } else {
            this.withdraw(amount);
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.userId);
            return true;
        }
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

// ATM Interface
class ATM {
    private Account userAccount;
    private Scanner scanner = new Scanner(System.in);

    public ATM(Account account) {
        this.userAccount = account;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\nATM MENU");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    userAccount.printTransactionHistory();
                    break;
                case 2:
                    handleWithdraw();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you for using the ATM!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleWithdraw() {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        userAccount.withdraw(amount);
    }

    private void handleDeposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        userAccount.deposit(amount);
        System.out.println("Deposit successful!");
    }

    private void handleTransfer() {
        System.out.print("Enter recipient user ID: ");
        String recipientId = scanner.next();
        System.out.print("Enter amount to transfer: $");
        double amount = scanner.nextDouble();

        Account recipient = new Account(recipientId, "1234", 0);
        if (userAccount.transfer(recipient, amount)) {
            System.out.println("Transfer successful to user " + recipientId);
        }
    }
}

// Main class to start the ATM system
public class ATMInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        Account userAccount = new Account("user123", "4321", 1000);

        if (userAccount.validateUser(userId, pin)) {
            ATM atm = new ATM(userAccount);
            atm.showMenu();
        } else {
            System.out.println("Invalid credentials! Exiting...");
        }
    }
}
