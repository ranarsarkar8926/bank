import java.util.*;

class BankAccount {
    private int accountNumber;
    private String accountHolderName;
    private float balance;
    private String atmPin;

    public BankAccount(int accNum, String name, String pin, float bal) {
        this.accountNumber = accNum;
        this.accountHolderName = name;
        this.balance = bal;
        this.atmPin = pin;
    }

    public void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: $" + balance);
    }

    public void deposit(float amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + ". New Balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public boolean withdraw(float amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + ". Remaining Balance: $" + balance);
            return true;
        } else {
            System.out.println("Invalid withdrawal amount!");
            return false;
        }
    }

    public void setAtmPin(String pin) {
        this.atmPin = pin;
        System.out.println("ATM PIN set successfully.");
    }

    public boolean verifyAtmPin(String pin) {
        return this.atmPin.equals(pin);
    }

    public float getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}

class Bank {
    private List<BankAccount> accounts = new ArrayList<>();
    private Set<Integer> usedAccountNumbers = new HashSet<>();
    private Random random = new Random();

    private int generateUniqueAccountNumber() {
        int accNum;
        do {
            accNum = 100000 + random.nextInt(900000);
        } while (usedAccountNumbers.contains(accNum));
        usedAccountNumbers.add(accNum);
        return accNum;
    }

    public void createAccount(String name, String pin, float initialDeposit) {
        int accNum = generateUniqueAccountNumber();
        BankAccount newAccount = new BankAccount(accNum, name, pin, initialDeposit);
        accounts.add(newAccount);
        System.out.println("Account created successfully! Your Account Number is: " + accNum);
    }

    public void displayAccount(int accNum) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNum) {
                acc.displayDetails();
                return;
            }
        }
        System.out.println("Account not found!");
    }

    public void depositToAccount(int accNum, float amount) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNum) {
                acc.deposit(amount);
                return;
            }
        }
        System.out.println("Account not found!");
    }

    public void withdrawFromAccount(int accNum, float amount) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNum) {
                acc.withdraw(amount);
                return;
            }
        }
        System.out.println("Account not found!");
    }

    public void setAtmPinForAccount(int accNum, String pin) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNum) {
                acc.setAtmPin(pin);
                return;
            }
        }
        System.out.println("Account not found!");
    }

    public void atmLogin(int accNum, String pin) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNum) {
                if (acc.verifyAtmPin(pin)) {
                    atmOperations(acc);
                } else {
                    System.out.println("Invalid ATM PIN!");
                }
                return;
            }
        }
        System.out.println("Account not found!");
    }

    private void atmOperations(BankAccount account) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        float amount;

        do {
            System.out.println("\n*** ATM Menu ***");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Exit ATM");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: $" + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter Withdrawal Amount: ");
                    amount = scanner.nextFloat();
                    account.withdraw(amount);
                    break;
                case 3:
                    System.out.println("Exiting ATM...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 3);
    }
}

public class BankSystem {
    public static boolean validatePin(String pin) {
        if (pin.length() != 4) return false;
        for (char ch : pin.toCharArray()) {
            if (!Character.isDigit(ch)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n*** Bank Management System ***");
            System.out.println("1. Bank Authority");
            System.out.println("2. User (ATM)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.println("\n*** Bank Authority Menu ***");
                    System.out.println("1. Create Account");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. Display Account");
                    System.out.println("5. Set ATM PIN");
                    System.out.print("Enter your choice: ");
                    int subChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    switch (subChoice) {
                        case 1:
                            System.out.print("Enter Account Holder Name: ");
                            String name = scanner.nextLine();

                            String pin;
                            do {
                                System.out.print("Set 4-digit ATM PIN: ");
                                pin = scanner.next();
                            } while (!validatePin(pin));

                            System.out.print("Enter Initial Deposit: ");
                            float initialDeposit = scanner.nextFloat();

                            bank.createAccount(name, pin, initialDeposit);
                            break;

                        case 2:
                            System.out.print("Enter 6-digit Account Number: ");
                            int accNum = scanner.nextInt();
                            System.out.print("Enter Deposit Amount: ");
                            float depositAmount = scanner.nextFloat();
                            bank.depositToAccount(accNum, depositAmount);
                            break;

                        case 3:
                            System.out.print("Enter 6-digit Account Number: ");
                            accNum = scanner.nextInt();
                            System.out.print("Enter Withdrawal Amount: ");
                            float withdrawalAmount = scanner.nextFloat();
                            bank.withdrawFromAccount(accNum, withdrawalAmount);
                            break;

                        case 4:
                            System.out.print("Enter 6-digit Account Number: ");
                            accNum = scanner.nextInt();
                            bank.displayAccount(accNum);
                            break;

                        case 5:
                            System.out.print("Enter 6-digit Account Number: ");
                            accNum = scanner.nextInt();

                            do {
                                System.out.print("Set 4-digit ATM PIN: ");
                                pin = scanner.next();
                            } while (!validatePin(pin));

                            bank.setAtmPinForAccount(accNum, pin);
                            break;

                        default:
                            System.out.println("Invalid choice!");
                    }
                    break;
                }
                case 2:
                    System.out.print("Enter 6-digit Account Number: ");
                    int accNum = scanner.nextInt();
                    System.out.print("Enter 4-digit ATM PIN: ");
                    String pin = scanner.next();
                    bank.atmLogin(accNum, pin);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 3);

        scanner.close();
    }
}
