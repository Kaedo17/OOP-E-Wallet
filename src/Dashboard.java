import java.util.Scanner;

public class Dashboard {
    public static class balanceBanner extends Auth.Banner {
        public static void balanceBannerShow() {
            System.out.println("O---------------------------------------O");
            System.out.println("|             B A L A N C E             |");
            System.out.println("O---------------------------------------O");
            System.out.println("|                 $1000                 |");
            System.out.println("O---------------------------------------O");

        }
    }

    public void showBalance() {
        balanceBanner.balanceBannerShow();
    }

    /**
     * Interactive dashboard loop. Returns when the user chooses to logout/exit.
     */
    public void displayDashboard(Scanner input) {
        boolean logout = false;
        while (!logout) {
            Auth.clearConsole();
            balanceBanner.balanceBannerShow();
            System.out.println("1. Refresh balance");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            String choice = input.nextLine().trim();
            if ("1".equals(choice)) {
                
            } else if ("2".equals(choice)) {
                logout = true;
            } else {
                System.out.println("O---------------------------------------O");
                System.out.println("Invalid option.");
                System.out.println("O---------------------------------------O");
                Auth.pause(input);
            }

        }
        Auth.clearConsole();
    }
}
