import java.util.Scanner;

public class Dashboard {
    private final Banners banners = new Banners();

    public void displayDashboard(Scanner input) {
        boolean logout = false;
        while (!logout) {
            Auth.clearConsole();
            BalanceManager balanceManager = new BalanceManager("", Auth.getLoggedInUsername(), "", Auth.getLoggedInPin());
            banners.getBalanceBanner().bannerShow();
            balanceManager.showBalance();

            banners.getBalanceBanner().balanceBannerOpts();
            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();
            if (null == choice) {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid option.");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
            } else
                switch (choice) {
                    case "1":
                        break;
                    case "2":
                        Deposit deposit = new Deposit();
                        deposit.showDeposit(input);
                        break;
                    case "3":
                        Transfer transfer = new Transfer();
                        transfer.showTransfer(input);
                        break;
                    case "4":
                        TransacHistory history = new TransacHistory("", Auth.getLoggedInUsername(), "transfer");
                        history.showHistory();
                        Auth.pause(input);
                        break;
                    case "5":
                        logout = true;
                        break;
                    default:
                        System.out.println("O---------------------------------------O");
                        System.out.println("Invalid option.");
                        System.out.println("O---------------------------------------O");
                        Auth.pause(input);
                        break;
                }

        }
        Auth.clearConsole();
    }
}
