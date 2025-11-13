import java.util.Scanner;

public class Transfer {
    String username = Auth.getLoggedInUsername();
    int pin = Auth.getLoggedInPin();
    BalanceManager transferBalance = new BalanceManager("", username, pin);
    Banners balanceBanner = new Banners();

    public void showTransfer(Scanner input) {
        boolean success = false;
        while (!success) {
            Auth.clearConsole();
            balanceBanner.new BalanceBanner().bannerShow();
            transferBalance.showBalance();
            balanceBanner.new BalanceBanner().bannerSingleOpt();
            System.out.print("Enter amount: ");
            String amount = input.nextLine().trim();

            if ("1".equals(amount)) {
                return; // Go back to dashboard
            }

            try {
                long depositAmount = Long.parseLong(amount);
                if (depositAmount <= 0) {
                    System.out.println("O---------------------------------------O");
                    System.err.println("Amount must be positive!");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);

                } else if (depositAmount > 1000000000) { // Limit to 1 billion
                    System.out.println("O---------------------------------------O");
                    System.err.println("Amount too large! Maximum deposit is $1,000,000,000");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);
                } else {
                    transferBalance.setTransfer((long) depositAmount);
                    transferBalance.transferBalance();
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
