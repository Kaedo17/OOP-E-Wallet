import java.util.Scanner;

public class Deposit {
    String username = Auth.getLoggedInUsername();
    int pin = Auth.getLoggedInPin();
    BalanceManager addBalance = new BalanceManager("", username, pin);
    Banners balanceBanner = new Banners();

    public void showDeposit(Scanner input) {
        boolean success = false;
        while (!success) {
            Auth.clearConsole();
            balanceBanner.new depositBanner().bannerShow();
            addBalance.showBalance();
            balanceBanner.new depositBanner().bannerSingleOpt();
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

                } else if (depositAmount > 1000000000) { // Limit to 1 billion
                    System.out.println("O---------------------------------------O");
                    System.err.println("Amount too large! Maximum deposit is $1,000,000,000");
                    System.out.println("O---------------------------------------O");
                    Auth.pause(input);
                } else {
                    addBalance.setBalance((long) depositAmount);
                    addBalance.addBalance();
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
