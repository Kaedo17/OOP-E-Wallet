import java.util.Scanner;

public class Transfer {
    String username = Auth.getLoggedInUsername();
    int pin = Auth.getLoggedInPin();
    BalanceManager transferBalance = new BalanceManager("", username, "", pin);
    CurrentBalance currentBalance = new CurrentBalance(username, pin);
    Banners balanceBanner = new Banners();
    TransacHistory history = new TransacHistory("", username, "transfer");

    public void showTransfer(Scanner input) {
        boolean success = false;
        while (!success) {
            Auth.clearConsole();
            balanceBanner.new TransferBanner().bannerShow();
            transferBalance.showBalance();
            balanceBanner.new TransferBanner().bannerSingleOpt();
            System.out.print("Recipient: ");
            String toUser = input.nextLine().trim();

            if (":1".equals(toUser)) {
                return;
            } else if (toUser.isEmpty() || toUser.matches("[0-9-]+")) {
                System.out.println("O------------------------------------------------O");
                System.out.println("Invalid username.");
                System.out.println("O------------------------------------------------O");
                Auth.pause(input);
                continue;
            }

            System.out.print("Enter amount: ");
            String amount = input.nextLine().trim();

            if (":1".equals(amount)) {
                continue;
            }

            try {
                long depositAmount = Long.parseLong(amount);
                if (depositAmount <= 0) {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Amount must be positive!");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);

                } else if (currentBalance.currentBalance() < depositAmount) {
                    System.out.println("O---------------------------------------O");
                    System.out.println("Insufficient Balance!");
                    System.out.println("Current balance: ₱" + currentBalance.currentBalance());
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);
                } else if (depositAmount > 1000000000) { // Limit to 1 billion
                    System.out.println("O---------------------------------------O");
                    System.out.println("Amount too large! Maximum deposit is ₱1,000,000,000");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);
                } else {
                    transferBalance.setRecipient(toUser);
                    transferBalance.setTransfer((long) depositAmount);
                    boolean transferSuccess = transferBalance.transferBalance();

                    if (transferSuccess) {
                        history.setAmount(String.valueOf("-" + depositAmount));
                        history.setType("transfer to " + toUser);
                        history.addTransac();
                    }
                    Auth.pause(input);
                    success = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid amount! Please enter numbers only.");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
            }
        }
    }
}
