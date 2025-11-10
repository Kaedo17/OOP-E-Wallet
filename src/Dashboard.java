import java.util.Scanner;

public class Dashboard {

    Banners balanceBanner = new Banners();
    // Auth auth = new Auth(null, 0);

    /**
     * Interactive dashboard loop. Returns when the user chooses to logout/exit.
     */
    public void displayDashboard(Scanner input) {
        boolean logout = false;
        while (!logout) {
            Auth.clearConsole();
            BalanceManager balanceManager = new BalanceManager("", Auth.getLoggedInUsername(), Auth.getLoggedInPin());
            balanceBanner.new BalanceBanner().bannerShow();
            balanceManager.showBalance();

            System.out.println("1. Refresh balance");
            System.out.println("2. Logout");
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
