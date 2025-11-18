import java.util.Scanner;

public class Transfer {
    String username = Auth.getLoggedInUsername();
    int pin = Auth.getLoggedInPin();
    BalanceManager transferBalance = new BalanceManager("", username, pin);
    CurrentBalance currentBalance = new CurrentBalance(username, pin);
    Banners balanceBanner = new Banners();
    TransacHistory history = new TransacHistory("", username, "transfer");

    public void showTransfer(Scanner input) {
        boolean success = false;
        while (!success) {
            Auth.clearConsole();
            balanceBanner.new transferBanner().bannerShow();
            transferBalance.showBalance();
            balanceBanner.new transferBanner().bannerSingleOpt();
            System.out.print("Enter amount: ");
            String amount = input.nextLine().trim();

            if (":1".equals(amount)) {
                return; // Go back to dashboard
            }

            try {
                long depositAmount = Long.parseLong(amount);
                if (depositAmount <= 0) {
                    System.out.println("O---------------------------------------O");
                    System.err.println("Amount must be positive!");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);

                } else if (currentBalance.currentBalance() < depositAmount) {
                    System.out.println("O---------------------------------------O");
                    System.err.println("Insufficient Balance!");
                    System.out.println("Current balance: $" + currentBalance.currentBalance());
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);
                } else if (depositAmount > 1000000000) { // Limit to 1 billion
                    System.out.println("O---------------------------------------O");
                    System.err.println("Amount too large! Maximum deposit is $1,000,000,000");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);
                } else {
                    history.setAmount(String.valueOf(depositAmount));
                    history.setType("transfer");
                    history.addTransac();
                    transferBalance.setTransfer((long) depositAmount);
                    transferBalance.transferBalance();
                    Auth.pause(input);
                    success = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("O---------------------------------------O");
                System.err.println("Invalid amount! Please enter numbers only.");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
            }
        }
    }
}
